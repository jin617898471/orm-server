package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmCode;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmCodeDao;
import cn.innosoft.fw.orm.server.persistent.OrmSystemDao;

@Service
public class OrmCodeService extends AbstractBaseService<OrmCode, String> {

	@Autowired
	private OrmCodeDao ormCodeDao;
	
	@Autowired
	private OrmSystemDao ormSystemDao;
	
	@Override
	public BaseDao<OrmCode, String> getBaseDao() {
		return ormCodeDao;
	}

	/**
	 * 代码查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmCode> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmCode> page = findAll(group, pageRequest);
		return page;
	}

	public void addCode(OrmCode ormCode) {
		ormCodeDao.save(ormCode);
	}

	public void updateCode(OrmCode ormCode) {
		ormCodeDao.update(ormCode);
	}

	public void deleteCode(String codeId) {
		ormCodeDao.delete(codeId);
	}

	public void deleteBatchCode(ArrayList<String> idArray){
		for (int i = 0; i < idArray.size(); i++) {
		 	ormCodeDao.delete(idArray.get(i));
		}
	}


	/**
	 * 将OrmCode生成代码ztree节点，建模时用
	 * 
	 * @param res
	 * @return
	 */
	public ZtreeBean codeToTreeNode(OrmCode code) {
		ZtreeBean node = new ZtreeBean();
		//节点ID
		node.setId(code.getCodeId());
		//父节点ID
		node.setpId(code.getParentCodeId());
		//父节点名称
		node.setName(code.getCodeName());
		//是否是父节点
		boolean isParent = "Y".equals(code.getIsLeaf()) ? false : true;
		//是否为打开状态
		node.setOpen(isParent);
		//前端可能需要用到的信息属性
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("rootCodeId", code.getRootCodeId());
		node.setAttributes(attributes);
		//是否隐藏单复选框
		node.setNocheck(false);
		//是否选中
		node.setChecked(false);
		
		return node;
	}
	
	/**
	 * 获取整个代码树
	 * @return
	 */
	public List<ZtreeBean> getAllCodeTreeNodes(){
		
		List<ZtreeBean> ztree = new ArrayList<ZtreeBean>();
		
		ZtreeBean grobalRootNode = new ZtreeBean();
		grobalRootNode.setId("ROOT1");
		grobalRootNode.setName("全局系统代码");
		grobalRootNode.setIsParent(true);
		grobalRootNode.setNocheck(false);
		grobalRootNode.setOpen(true);
		grobalRootNode.setpId(null);
		grobalRootNode.setChecked(false);
		
		ztree.add(grobalRootNode);
		
		List<OrmCode> globalAllCodes = new ArrayList<OrmCode>();
		
		//获取全局代码根节点
		//获取全局根节点代码,即系统Id为"grobal"且父节点id为"ROOT"的Code
		List<OrmCode> globalRootCodes = ormCodeDao.findBySystemIdAndParentCodeId("GLOBAL","ROOT");
		
		//获取所有系统,并递归调用获取节点代码
		for(int i=0;i<globalRootCodes.size();i++){
			OrmCode globalRootCode = globalRootCodes.get(i);
			globalRootCode.setParentCodeId("ROOT1"); 
			globalAllCodes.add(globalRootCode);
			List<OrmCode> globalNormalCodes = ormCodeDao.findByParentCodeId(globalRootCode.getCodeId());
			globalAllCodes.addAll(globalNormalCodes);
		}
		
		//获取所有的系统
		List<OrmSystem> ormSystems = ormSystemDao.findByValidSign("Y");
		
		for(int i=0;i<ormSystems.size();i++){
			OrmSystem ormSystem = ormSystems.get(i);
			String systemId = ormSystem.getSystemId();
			String systemName = ormSystem.getSystemName();
			
			//创建显示的树系统节点
			ZtreeBean systemNode = new ZtreeBean();
			systemNode.setId("ROOT2");
			systemNode.setName(systemName);
			systemNode.setIsParent(true);
			systemNode.setOpen(true);
			systemNode.setpId(null);
			systemNode.setNocheck(true);
			
			ztree.add(systemNode);
			
			//根据系统查找属于该系统的代码
			List<OrmCode> systemRootCodes = ormCodeDao.getRootCodeBySystemId(systemId); 
			for(int j=0;j<systemRootCodes.size();j++){
				
				OrmCode systemRootCode = systemRootCodes.get(j);
				systemRootCode.setParentCodeId("ROOT2");
				globalAllCodes.add(systemRootCode);
				List<OrmCode> systemNormalCodes = ormCodeDao.findByParentCodeId(systemRootCode.getCodeId());
				globalAllCodes.addAll(systemNormalCodes);
			}
		}
		
		for(int i=0;i<globalAllCodes.size();i++){
			ZtreeBean treeNode = codeToTreeNode(globalAllCodes.get(i));
			ztree.add(treeNode);
		}
		
		return ztree;
	}
	
	
	
	public List<OrmCode> findOrmCodesByParentCodeId(String parentCodeId){
		return ormCodeDao.findByParentCodeId(parentCodeId);
	}
}
