package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.biz.utils.Identities;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmRoleService extends AbstractBaseService<OrmRole, String> {

	@Autowired
	private OrmRoleDao ormRoleDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;
	@Autowired
	private OrmResourceService ormResourceService;

	@Override
	public BaseDao<OrmRole, String> getBaseDao() {
		return ormRoleDao;
	}

	public void addRole(OrmRole ormRole) {
		ormRole.setRoleId(Identities.uuid2());
		ormRole.setRoleType("NORMAL");
		add(ormRole);
	}
	
	public void addAdminRole(String systemId) {
		OrmRole ormRole = new OrmRole();
		ormRole.setSystemId(systemId);
		ormRole.setRoleId(Identities.uuid2());
		ormRole.setRoleNameCn("系统管理员");
		ormRole.setRoleType("ADMIN");
		add(ormRole);
	}
	
	public void updateRole(OrmRole ormRole, List<String> updateField) {
		updateSome(ormRole,updateField);
	}
	
	public void deleteRole(String id) {
		delete(id);
		ormRoleResourceRightDao.deleteByRoleId(id);
		ormOrgRoleMapDao.deleteByRoleId(id);
		ormUserRoleMapDao.deleteByRoleId(id);
	}

	public void deleteRole(ArrayList<String> idArray) {
		delete(idArray);
		ormRoleResourceRightDao.deleteByRoleIdIn(idArray);
		ormOrgRoleMapDao.deleteByRoleIdIn(idArray);
		ormUserRoleMapDao.deleteByRoleIdIn(idArray);
	}
	
	public void deleteBySystemId(String systemId) {
		ormRoleDao.deleteBySystemId(systemId);
		ormRoleResourceRightDao.deleteBySystemId(systemId);
		ormOrgRoleMapDao.deleteBySystemId(systemId);
		ormUserRoleMapDao.deleteBySystemId(systemId);
	}
	
	
	public List<ZtreeBean> findUserResourceTrees(String userId) {
		List<ZtreeBean> tree = ormResourceService.findUserResourceTrees(userId);
		return tree;
	}
	
	public List<ZtreeBean> findOrgResourceTrees(String orgId) {
		List<ZtreeBean> tree = ormResourceService.findOrgResourceTrees(orgId);
		return tree;
	}

	
	
}
