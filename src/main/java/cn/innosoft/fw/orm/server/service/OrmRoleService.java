package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.biz.utils.Identities;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
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
		updateSome(ormRole, updateField);
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

	public List<Map<String, Object>> getOrgNotAssignRole(String orgId, String roleName, String systemName) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("orgId", orgId);
		if (roleName != null) {
			args.put("roleName", "%" + roleName + "%");
		}
		if (systemName != null) {
			args.put("systemId", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("orgRole-notAssign", args);
		return list;
	}

	public List<Map<String, Object>> getOrgAssignRole(String orgId, String roleName, String systemName) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("orgId", orgId);
		if (roleName != null) {
			args.put("roleName", "%" + roleName + "%");
		}
		if (systemName != null) {
			args.put("systemId", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("orgRole-assign", args);
		return list;
	}

	public List<Map<String, Object>> getUserNotAssignRole(String userId, String roleName, String systemName) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userId", userId);
		if (roleName != null) {
			args.put("roleName", "%" + roleName + "%");
		}
		if (systemName != null) {
			args.put("systemId", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("userRole-notAssign", args);
		return list;
	}

	public List<Map<String, Object>> getUserAssignRole(String userId, String roleName, String systemName) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userId", userId);
		if (roleName != null) {
			args.put("roleName", "%" + roleName + "%");
		}
		if (systemName != null) {
			args.put("systemId", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("userRole-assign", args);
		return list;
	}

	public List<ZtreeBean> getRoleResourceTrees(String roleId) {
		OrmRole role = ormRoleDao.findOne(roleId);
		List<ZtreeBean> tree = ormResourceService.findRoleResourceTrees(roleId, role.getSystemId());
		return tree;
	}

	public void saveRoleResourceRight(String roleId, String resources) {
		String systemId = findOne(roleId).getSystemId();
		List<String> res = getList(resources);
		List<OrmRoleResourceRight> old = ormRoleResourceRightDao.findByRoleId(roleId);
		List<String> roleOwnResourceList = getResourceId(old);

		List<String> needDeleteResourceIdList = new ArrayList<String>();
		for (int i = roleOwnResourceList.size() - 1; i >= 0; i--) {
			String ores = roleOwnResourceList.get(i);
			if (res.contains(ores)) {
				res.remove(ores);
				roleOwnResourceList.remove(i);
			} else {
				needDeleteResourceIdList.add(ores);
			}
		}
		ormRoleResourceRightDao.deleteByRoleIdAndResourceIdIn(roleId, needDeleteResourceIdList);
		for (String resId : res) {
			OrmRoleResourceRight obj = new OrmRoleResourceRight();
			obj.setResourceId(resId);
			obj.setRoleId(roleId);
			obj.setSystemId(systemId);
			ormRoleResourceRightDao.add(obj);
		}
	}

	private List<String> getResourceId(List<OrmRoleResourceRight> old) {
		List<String> res = new ArrayList<String>();
		for (OrmRoleResourceRight obj : old) {
			res.add(obj.getResourceId());
		}
		return res;
	}

	private List<String> getList(String resources) {
		List<String> res = new ArrayList<String>();
		if (null != resources && resources.trim().length() > 0) {
			List<String> ress = Arrays.asList(resources.split(","));
			res.addAll(ress);
		}
		return res;
	}

}
