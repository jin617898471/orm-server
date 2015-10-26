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
import cn.innosoft.fw.orm.server.model.OrmRoleCodeRight;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmCodeDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleCodeRightDao;

@Service
public class OrmCodeService extends AbstractBaseService<OrmCode, String> {

	@Autowired
	private OrmCodeDao ormCodeDao;
	@Autowired
	private OrmRoleCodeRightDao ormRoleCodeRightDao;
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
		ormRoleCodeRightDao.deleteByCodeId(codeId);
	}

	public void editRoleCodeRight(List<String> roleIds, OrmCode code) {
		String codeId = code.getCodeId();
		String systemId = code.getSystemId();
		String parentCodeId = code.getParentCodeId();
		for (String roleId : roleIds) {
			List<OrmRoleCodeRight> list = ormRoleCodeRightDao.findByCodeIdAndRoleId(parentCodeId, roleId);
			if (list.size() == 0) {
				createOrmRoleCodeRight(roleId, parentCodeId, "Y", "GLOBAL", null,
						systemId);
			}
			createOrmRoleCodeRight(roleId, codeId, "N", "GLOBAL", null, systemId);
		}
	}

	/**
	 * 生成ZTREE，权限建模的时候用
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ZtreeBean> createZtree(String roleId) {
		List<ZtreeBean> result = new ArrayList<ZtreeBean>();
		List<OrmCode> codes = ormCodeDao.findAll();
		List<OrmRoleCodeRight> rights = ormRoleCodeRightDao.findByRoleId(roleId);
		for (OrmCode code : codes) {
			ZtreeBean node = codeToTreeNode(code);
			String codeId = code.getCodeId();
			for (OrmRoleCodeRight rcr : rights) {
				if (codeId.equals(rcr.getCodeId()) && "N".equals(rcr.getHalfSelect())) {
					node.setChecked(true);
				}
			}
			result.add(node);
		}
		return result;
	}

	/**
	 * 生成代码ztree，建模时用
	 * 
	 * @param res
	 * @return
	 */
	private ZtreeBean codeToTreeNode(OrmCode code) {
		ZtreeBean node = new ZtreeBean();
		node.setId(code.getCodeId());
		node.setpId(code.getParentCodeId());
		node.setName(code.getCodeName());
		boolean isParent = code.getIsLeaf() == "Y" ? false : true;
		node.setOpen(isParent);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("rootCodeId", code.getRootCodeId());
		node.setAttributes(attributes);
		node.setNocheck(false);
		node.setChecked(false);
		return node;
	}
	public void createOrmRoleCodeRight(String roleId, String codeId, String halfSelcet, String type, String resId,
			String systemId) {
		OrmRoleCodeRight rcr = new OrmRoleCodeRight();
		rcr.setCodeId(codeId);
		rcr.setHalfSelect(halfSelcet);
		if (null != resId) {
			rcr.setResourceId(resId);
		}
		rcr.setRoleId(roleId);
		rcr.setSystemId(systemId);
		rcr.setType(type);
		ormRoleCodeRightDao.save(rcr);
	}
}
