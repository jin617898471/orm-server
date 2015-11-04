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
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
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
	private OrmResourceService ormResourceService;

	/**
	 * 重载方法
	 */
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

	/**
	 * 更新角色
	 * 
	 * @param ormRole
	 */
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

	/**
	 * 通过角色Id查找角色
	 * 
	 * @param id
	 * @return
	 */
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

	/**
	 * 查找所有有效角色Id
	 * 
	 * @return
	 */
	public List<String> findValidRoleIdList() {
		return ormRoleDao.findRoleIds();
	}

	/**
	 * 给角色授权<主方法>
	 * 
	 * @param roleId
	 * @param resourceIds
	 */
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

	/**
	 * 对比角色已授资源权限，将新增资源增加，删除资源删除
	 * 
	 * @param roleId
	 * @param resourceIdsList
	 */
	public void grantResource(String roleId, List<String> resourceIdsList) {
		List<String> roleOwnResourceList = getResourceIdsByRoleId(roleId);
		List<String> needAddResourceIdList = new ArrayList<String>();
		StringBuffer needDelResourceIds = new StringBuffer();
		List<String> needUpdateResourceIdList = updateParentRes(roleId, resourceIdsList);
		// 角色已授资源权限为空直接新增
		if (roleOwnResourceList.size() < 1) {
			addResRoleRight(roleId, resourceIdsList);
		} else {// 整理新增资源id列表
			for (int i = 0; i < resourceIdsList.size(); i++) {
				String id = resourceIdsList.get(i);
				if (!roleOwnResourceList.contains(id)) {// 以前不包含此ResourceId
					needAddResourceIdList.add(id);
				}
			}
			// 整理需删除资源id字符串
			for (int i = 0; i < roleOwnResourceList.size(); i++) {
				String resourceId = roleOwnResourceList.get(i);
				if (!resourceIdsList.contains(resourceId) && !needUpdateResourceIdList.contains(resourceId)) {// 此次不包含此ResourceId
					needDelResourceIds.append(resourceId);
					needDelResourceIds.append(",");
				}
			}
			// 删除资源权限
			deletes(roleId,needDelResourceIds);
			// 增加资源权限
			if (needAddResourceIdList.size() > 0) {
				addResRoleRight(roleId, needAddResourceIdList);
			}
		}
	}

	/**
	 * 通过资源Id查找父资源，如果父资源在资源链表中，半选状态为N,否则为Y
	 * 
	 * @param roleId
	 * @param resourceIdsList
	 * @return
	 */
	public List<String> updateParentRes(String roleId, List<String> resourceIdsList) {
		List<String> needUpdateResourceIdList = new ArrayList<String>();
		for (String s : resourceIdsList) {
			OrmResource ormRRR = ormResourceService.findByReosurceId(s);
			String parentResId = ormRRR.getParentResId();
			while (!"ROOT".equals(parentResId.toUpperCase())) {
				if (!needUpdateResourceIdList.contains(parentResId)) {// 已经判断过的跳过
					needUpdateResourceIdList.add(parentResId);
					if (resourceIdsList.contains(parentResId)) {
						modifyHalfSelect(roleId, parentResId, "N");
					} else {
						modifyHalfSelect(roleId, parentResId, "Y");
					}
				}
				parentResId = ormResourceService.findByReosurceId(parentResId).getParentResId();
			}
		}
		return needUpdateResourceIdList;
	}

	/**
	 * 修改父亲资源的半选状态,如果没有记录，则新增一条记录，有则更新半选状态
	 * 
	 * @param roleId
	 * @param parentResId
	 * @param halfSelect
	 */
	private void modifyHalfSelect(String roleId, String parentResId, String halfSelect) {
		List<OrmRoleResourceRight> roleResRight = ormRoleResourceRightDao.findByRoleIdAndResourceId(roleId,
				parentResId);
		if (roleResRight != null && roleResRight.size() > 0) {
			ormRoleResourceRightDao.updateHalfSelect(roleId, parentResId, halfSelect);
		} else {
			OrmRoleResourceRight ormAuthRole = new OrmRoleResourceRight();
			ormAuthRole.setResourceId(parentResId);
			ormAuthRole.setRoleId(roleId);
			ormAuthRole.setHalfSelect(halfSelect);
			ormAuthRole.setSystemId(ormResourceService.findByReosurceId(parentResId).getSystemId());
			ormRoleResourceRightDao.add(ormAuthRole);
		}
	}

	/**
	 * 在资源角色中间表中查找角色所有的资源Id
	 * 
	 * @param roleId
	 * @return
	 */
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

	/**
	 * 新增授权记录
	 * 
	 * @param roleId
	 * @param resourceIdList
	 */
	private void addResRoleRight(String roleId, List<String> resourceIdList) {
		for (int i = 0; i < resourceIdList.size(); i++) {
			OrmRoleResourceRight ormAuthRole = new OrmRoleResourceRight();
			String id = resourceIdList.get(i);
			ormAuthRole.setResourceId(id);
			ormAuthRole.setRoleId(roleId);
			ormAuthRole.setHalfSelect("N");
			ormAuthRole.setSystemId(ormResourceService.findByReosurceId(id).getSystemId());
			ormRoleResourceRightDao.add(ormAuthRole);
		}
	}

	/**
	 * 删除角色的资源授权记录
	 * 
	 * @param roleId
	 * @param resourceIds
	 */
	public void deletes(String roleId, StringBuffer needDelResourceIds) {
		if (needDelResourceIds.length() > 0) {
			String resourceIds = needDelResourceIds.toString();
			resourceIds = resourceIds.substring(0, resourceIds.length() - 1);
			String[] resIds = resourceIds.split(",");
			for (int i = 0; i < resIds.length; i++) {
				deleteResRight(roleId, resIds[i]);
			}
		}
	}

	/**
	 * 删除对应角色该资源的记录
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void deleteResRight(String roleId, String resourceId) {
		ormRoleResourceRightDao.deleteByRoleIdAndResourceId(roleId, resourceId);
	}

	public List<OrmRole> getRoleByuserId(String userId){
		return ormRoleDao.getRoleByUserid(userId);
	}
	/**
	 * 通过UserId查找对应的角色
	 * MAP_TYPE(用户分配角色类型，USER_TO_ROLE：用户直接分配的角色；USER_ORG_TO_ROLE：用户通过所在岗位继承的角色)
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
	
	public List<Map<String, Object>> getUserAssignRole(String userId,String roleName,String systemName){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userId", userId);
		if(roleName != null){
			args.put("roleName", roleName);
		}
		if(systemName != null){
			args.put("systemName", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("userRole-assign", args);
		return findMapBySql("userRole-assign", args);
	}
	public List<Map<String, Object>> getUserNotAssignRole(String userId,String roleName,String systemName){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("userId", userId);
		if(roleName != null){
			args.put("roleName", roleName);
		}
		if(systemName != null){
			args.put("systemName", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("userRole-notAssign", args);
		return findMapBySql("userRole-notAssign", args);
	}
	public List<Map<String, Object>> getOrgAssignRole(String orgId,String roleName,String systemName){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("orgId", orgId);
		if(roleName != null){
			args.put("roleName", roleName);
		}
		if(systemName != null){
			args.put("systemName", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("orgRole-assign", args);
		return findMapBySql("orgRole-assign", args);
	}
	public List<Map<String, Object>> getOrgNotAssignRole(String orgId,String roleName,String systemName){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("orgId", orgId);
		if(roleName != null){
			args.put("roleName", roleName);
		}
		if(systemName != null){
			args.put("systemName", systemName);
		}
		List<Map<String, Object>> list = findMapBySql("orgRole-NotAssign", args);
		return findMapBySql("userRole-notAssign", args);
	}
}
