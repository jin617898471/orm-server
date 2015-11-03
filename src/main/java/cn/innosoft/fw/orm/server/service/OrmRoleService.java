package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmRoleService extends AbstractBaseService<OrmRole, String> {

	@Autowired
	private OrmRoleDao ormRoleDao;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;
	@Autowired
	private OrmOrgUserMapDao ormOrgUserMapDao;
	@Autowired
	private OrmResourceService ormResourceService;
	// @Autowired
	// private OrmCodeService ormCodeService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	@Autowired
	private OrmUserService ormUserService;
	// @Autowired
	// private OrmRoleResourceRightService ormRoleResMapService;

	@Override
	public BaseDao<OrmRole, String> getBaseDao() {
		return ormRoleDao;
	}

	/**
	 * 添加角色
	 * 
	 * @param ormRole
	 */
	public void addRole(OrmRole ormRole) {
		ormRoleDao.save(ormRole);
	}

	public void updateRole(OrmRole ormRole) {
		ormRoleDao.update(ormRole);
	}

	/**
	 * 删除角色和对应的其他表中记录
	 * 
	 * @param roleId
	 */
	public void deleteRole(String roleId) {
		ormRoleDao.delete(roleId);
		ormOrgRoleMapDao.deleteByRoleId(roleId);
		ormRoleResourceRightDao.deleteByRoleId(roleId);
		ormUserRoleMapDao.deleteByRoleId(roleId);
	}

	public void editRoleResourceRight(Map<String, OrmResource> map, String roleId) {
		List<OrmRoleResourceRight> rrrs = ormRoleResourceRightDao.findByRoleId(roleId);
		for (OrmRoleResourceRight rrr : rrrs) {
			if (map.containsKey(rrr.getResourceId())) {
				map.remove(rrr.getResourceId());
			} else {
				ormRoleResourceRightDao.delete(rrr);
			}
		}
		for (OrmResource res : map.values()) {
			String parentResId = res.getParentResId();
			List<OrmRoleResourceRight> list = ormRoleResourceRightDao.findByResourceId

			(parentResId);
			if (list.size() == 0) {
				ormResourceService.createOrmRoleResourceRight(roleId, parentResId, "Y",

				res.getSystemId());
			}
			ormResourceService.createOrmRoleResourceRight(roleId, res.getResourceId(), "N",

			res.getSystemId());
		}
	}

	public void editRoleOrgMap(List<String> orgIds, OrmRole role) {
		String roleId = role.getRoleId();
		List<OrmOrgRoleMap> maps = ormOrgRoleMapDao.findByRoleId(roleId);
		for (OrmOrgRoleMap orm : maps) {
			String orgId = orm.getOrgId();
			if (orgIds.contains(orgId)) {
				orgIds.remove(orgId);
			} else {
				ormOrgRoleMapDao.delete(orm);
				List<OrmOrgUserMap> list = ormOrgUserMapDao.findByOrgId(orgId);
				for (OrmOrgUserMap oum : list) {
					ormUserRoleMapDao.deleteByUserIdRoleIdAndType(oum.getUserId(),

					roleId, "USER_ORG_TO_ROLE");
				}
			}
		}
		for (String orgId : orgIds) {
			ormOrganizationService.createOrgRoleMap(orgId, role);
			List<OrmOrgUserMap> list = ormOrgUserMapDao.findByOrgId(orgId);
			for (OrmOrgUserMap oum : list) {
				ormUserService.createUserRoleMap(oum.getUserId(), role.getRoleId(), orgId,

				role.getSystemId());
			}
		}
	}

	public void addRoleUserMap(String userId, OrmRole role) {
		ormUserService.createUserRoleMap(userId, role.getRoleId(), null, role.getSystemId());
	}

	public void deleteRoleUserMap(String userId, String roleId) {
		ormUserRoleMapDao.deleteByUserIdAndRoleId(userId, roleId);
	}

	public OrmRole findByRoleId(String id) {
		return ormRoleDao.findByRoleId(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 */
	public void deleteByIds(ArrayList<String> idArray) {
		for (String string : idArray) {
			deleteRole(string);
		}
	}

	/**
	 * 分页查询有效数据
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmRole> findValid(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(),
				new String[] {

				"validSign" }, new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmRole> page = findAll(group, pageRequest);
		return page;
	}

	public List<String> findValidRoleIdList() {
		return ormRoleDao.findRoleIds();
	}

	public List<OrmRole> findRoleBySystemId(String systemId) {
		return ormRoleDao.findBySystemId(systemId);
	}

	public String getRoleUseOrgName(@PathVariable String idArray) {
		return getRoleUseOrgNames(idArray);
	}

	public String getRoleUseOrgNames(@PathVariable String idArray) {
		/*
		 * String[] str = idArray.split(","); String idstr = ""; String name =
		 * ""; String sql = ""; for (String string : str) { idstr += "'" +
		 * string.trim() + "',"; } sql = "from OrmRoleUOMap where roleId in (" +
		 * idstr.substring(0, idstr.length() - 1) + ")"; List<OrmRoleUOMap>
		 * names = this.getHibernateTemplate().find(sql); for (OrmRoleUOMap
		 * string : names) { OrmUser ormUser =
		 * this.getHibernateTemplate().get(OrmUser.class,
		 * string.getUserId().trim()); name += "[用户]: {" + ormUser.getUserAcct()
		 * + "}, "; OrmOrganization ormOrg =
		 * this.getHibernateTemplate().get(OrmOrganization.class,
		 * string.getOid().trim()); name += "[岗位]: {" + ormOrg.getOname() +
		 * "}, "; } return name;
		 */
		return "";
	}

	public void grantResource(String roleId, String resourceIds) {
		ArrayList<String> resourceIdsList = new ArrayList<String>();
		if (!resourceIds.isEmpty()) {
			String[] s = resourceIds.split(",");
			for (int i = 0; i < s.length; i++) {
				resourceIdsList.add(s[i]);
			}
		}
		grantResource(roleId, resourceIdsList);
	}

	// 对比角色已授资源权限，将新增资源增加，删除资源删除
	public void grantResource(String roleId, List<String> resourceIdsList) {
		List<String> roleOwnResourceList = getResourceIdsByRoleId(roleId);
		List<String> needAddResourceIdList = new ArrayList<String>();
		List<String> needUpdateResourceIdList = new ArrayList<String>();
		StringBuffer needDelResourceIds = new StringBuffer();

		// 通过资源Id查找父资源，如果父资源在资源链表中，半选状态为N,否则为Y
		for (String s : resourceIdsList) {
			OrmResource ormRRR = ormResourceService.findByReosurceId(s);
			String parentResId = ormRRR.getParentResId();
			if (!needUpdateResourceIdList.contains(parentResId)) {// 已经判断过的跳过
				if (roleOwnResourceList.contains(parentResId)) {
					needUpdateResourceIdList.add(parentResId);
					updateHalfSelect(roleId, parentResId, "N");
				} else {
					updateHalfSelect(roleId, parentResId, "Y");
				}
			}
		}

		// 角色已授资源权限为空直接新增
		if (roleOwnResourceList.size() < 1) {
			addAuthRole(roleId, resourceIdsList);
		} else {
			// 整理新增资源id列表
			for (int i = 0; i < resourceIdsList.size(); i++) {
				String id = resourceIdsList.get(i);
				if (!roleOwnResourceList.contains(id)) {// 以前不包含此ResourceId
					needAddResourceIdList.add(id);
				}
			}

			// 整理需删除资源id字符串
			for (int i = 0; i < roleOwnResourceList.size(); i++) {
				String resourceId = roleOwnResourceList.get(i);
				boolean isDelete = true;
				for (int j = 0; j < resourceIdsList.size(); j++) {
					String selectedResourceId = resourceIdsList.get(j);
					if (selectedResourceId.equals(resourceId)) {// 此次任然包含此ResourceId
						isDelete = false;
						break;
					}
				}
				if (isDelete) {// 此次不包含此ResourceId
					needDelResourceIds.append(resourceId);
					needDelResourceIds.append(",");
				}

			}

			// 删除资源权限
			if (needDelResourceIds.length() > 0) {
				String resourceIds = needDelResourceIds.toString();
				resourceIds = resourceIds.substring(0, resourceIds.length() - 1);
				deletes(roleId, resourceIds);
			}
			// 增加资源权限
			if (needAddResourceIdList.size() > 0) {
				addAuthRole(roleId, needAddResourceIdList);
			}
		}
	}

	/**
	 * 更新父亲资源的半选状态
	 * 
	 * @param roleId
	 * @param parentResId
	 * @param halfSelect
	 */
	private void updateHalfSelect(String roleId, String parentResId, String halfSelect) {
		ormRoleResourceRightDao.updateHalfSelect(roleId, parentResId, halfSelect);
	}

	public List<String> getResourceIdsByRoleId(String roleId) {
		List<String> resourceList = new ArrayList<String>();// 存放该角色下的所有资源id的集合
		FilterGroup filtergroup = QueryConditionHelper.add(new String[] { "roleId" },
				new String[] {

				roleId });
		List<OrmRoleResourceRight> list = ormRoleResourceRightDao.findAll(filtergroup);// 该角色下的所有资源
		for (OrmRoleResourceRight ormAuthRole : list) {
			resourceList.add(ormAuthRole.getResourceId());
		}
		return resourceList;
	}

	private void addAuthRole(String roleId, List<String> resourceIdList) {
		for (int i = 0; i < resourceIdList.size(); i++) {
			OrmRoleResourceRight ormAuthRole = new OrmRoleResourceRight();
			String id = resourceIdList.get(i);
			ormAuthRole.setResourceId(id);
			ormAuthRole.setRoleId(roleId);
			ormAuthRole.setHalfSelect("N");
			// ormAuthRole.setSystemId(systemId);
			ormRoleResourceRightDao.add(ormAuthRole);
		}
	}

	public void deletes(String roleId, String resourceIds) {
		String[] resIds = resourceIds.split(",");
		for (int i = 0; i < resIds.length; i++) {
			deleteResAuth(roleId, resIds[i]);
		}
	}

	public void deleteResAuth(String roleId, String resourceId) {
		// String sql = "SELECT AUTH_ROLE_ID FROM T_FW_ORM_AUTH_ROLE WHERE
		// ROLE_ID = '" + roleId
		// + "' and RESOURCE_ID = '" + resourceId + "'";
		// List<OrmRoleResourceRight> list =
		// ormRoleResMapService.findEntityBySql(sql);
		// if (list.size() > 0) {
		// String authRoleId = list.get(0).getRoleId();
		// sql = "DELETE FROM T_FW_ORM_ROLE_O_MAP WHERE AUTH_ROLE_ID = '" +
		// authRoleId + "'";
		// this.executeUpdateBySql(sql);
		// sql = "DELETE FROM T_FW_ORM_ROLE_CODE_MAP WHERE AUTH_ROLE_ID = '" +
		// authRoleId + "'";
		// this.executeUpdateBySql(sql);
		// this.deleteRole(list.get(0).getId());
		// }
		ormRoleResourceRightDao.deleteByRoleIdAndResourceId(roleId, resourceId);
	}

	/**
	 * 通过UserId查找对应的角色
	 * MAP_TYPE(用户分配角色类型，USER_TO_ROLE：用户直接分配的角色；USER_ORG_TO_ROLE：用户通过所在岗位继
	 * 
	 * 承的角色)
	 * 
	 * @param userId
	 * @return List<map_type,role>
	 */
	public List<Object[]> findRoleInfoByUserId(String userId) {
		return ormUserRoleMapDao.findMapeTypeAndRole(userId);
	}

	/**
	 * 通过组织机构Id查找所有角色
	 * 
	 * @param orgId
	 * @return
	 */
	public List<OrmRole> findRoleByOrgId(String orgId) {
		return ormOrgRoleMapDao.findRoleByOrgId(orgId);
	}
}
