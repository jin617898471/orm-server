package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.persistent.OrmCodeDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmResourceDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmSystemDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmSystemService extends AbstractBaseService<OrmSystem, String> {

	@Autowired
	private OrmSystemDao ormSystemDao;
	@Autowired
	private OrmCodeService ormCodeService;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmResourceService ormResourceService;
	@Autowired
	private OrmRoleService ormRoleService;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;

	@Override
	public BaseDao<OrmSystem, String> getBaseDao() {
		return ormSystemDao;
	}


	
	/**
	 * 代码系统
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmSystem> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmSystem> page = findAll(group, pageRequest);
		return page;
	}

	
	/**
	 * 删除System
	 * 级联删除其他表里的数据
	 * @param systemId
	 */
	public void deleteSystem(String systemId) {
		ormSystemDao.delete(systemId);
		ormCodeService.deleteBySystemId(systemId);
		ormOrgRoleMapDao.deleteBySystemId(systemId);
		ormResourceService.deleteBySystemId(systemId);
		ormRoleService.deleteBySystemId(systemId);
		ormRoleResourceRightDao.deleteBySystemId(systemId);
		ormUserRoleMapDao.deleteBySystemId(systemId);
	}
	
	/**
	 * 批量删除System
	 * 
	 */
	public void deleteBatchSystemById(ArrayList<String> idArray){
		for(int i=0;i<idArray.size();i++){
			deleteSystem(idArray.get(i));
		}
	}
	public boolean checkSystemOnly(String SystemCode){
		List<OrmSystem> list = ormSystemDao.findBySystemCode(SystemCode);
		return list.size()==0;
	}
	/**
	 * 查询所有的OrmSystem记录
	 * @return
	 */
	public List<OrmSystem> findOrmSystemAll(){
		return ormSystemDao.findByValidSign("Y");
		//return null;
	}
	
	public String getSystemName(String systemId){
		return ormSystemDao.findOne(systemId).getSystemName();
	}
}
