package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrgRoleMap;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.OrmUserRoleMap;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrganizationDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;
import cn.innosoft.fw.orm.server.util.BeanMapSwitch;
import cn.innosoft.orm.client.service.LoginUserContext;

/**
 * 
 * @author Zouch
 * @date 2015年9月28日 上午10:17:48
 */
@Service
public class OrmUserService extends AbstractBaseService<OrmUser, String> {

	private static final Logger logger = LogManager.getLogger(OrmUserService.class);
	@Autowired
	private OrmUserDao ormUserDao;
	@Autowired
	private OrmOrgUserMapDao ormOrgUserMapDao;
	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;
	@Autowired
	private OrmRoleDao ormRoleDao;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmOrganizationDao ormOrganizationDao;

	@Override
	public BaseDao<OrmUser, String> getBaseDao() {
		return ormUserDao;
	}

	/**
	 * 查询用户
	 * 
	 * @param pageRequest
	 * @return
	 */
	public List<Map<String, Object>> find(PageRequest pageRequest) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmUser> page = findAll(group, pageRequest);
		List<OrmUser> users = page.getRows();
		List<String> orgIds = LoginUserContext.getOrgs();
		List<OrmOrgUserMap> oums = ormOrgUserMapDao.findByOrgIdIn(orgIds);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for(OrmOrgUserMap oum : oums){
			String userId = oum.getUserId();
			if (map.containsKey(userId)) {
				List<String> list = map.get(userId);
				list.add(oum.getOrgId());
			} else {
				List<String> list = new ArrayList<String>();
				list.add(oum.getOrgId());
				map.put(userId, list);
			}
		}
		for (OrmUser user : users) {
			String userId = user.getUserId();
			if (map.containsKey(userId)) {
				Map<String, Object> userWithOrg = BeanMapSwitch.beanToMap(user);
				List<String> tempOrgIds = map.get(userId);
				List<OrmOrganization> orgs = ormOrganizationDao.findByOrgIdIn(tempOrgIds);
				String orgNames = "";
				for(OrmOrganization org : orgs){
					if ("".equals(orgNames)) {
						orgNames += org.getOrgName();
					} else {
						orgNames += "," + org.getOrgName();
					}
				}
				userWithOrg.put("orgName", orgNames);
				result.add(userWithOrg);
			}
		}
		return result;
	}
	/**
	 * 添加用户
	 * 
	 * @param ormUser
	 * @return
	 */
	public boolean addUser(OrmUser ormUser) {
		boolean unique = checkUserUniqueness(ormUser);
		if (unique) {
			return false;
		} else {
			try {
				ormUserDao.save(ormUser);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	}

	/**
	 * 检查用户账号唯一性
	 * 
	 * @param ormUser
	 * @return
	 */
	public boolean checkUserUniqueness(OrmUser ormUser) {
		List<OrmUser> list = ormUserDao.findByUserAcct(ormUser.getUserAcct());
		return list.size() == 0 ? true : false;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param ormUser
	 * @param orgIds
	 * @return
	 */
	public boolean updateUser(OrmUser ormUser, List<String> orgIds) {
		try {
			ormUserDao.update(ormUser);
			String userId = ormUser.getUserId();
			editOrgUserMap(userId, orgIds);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 根据userId获取用户信息，具体情况根据前段页面再确定
	 * 
	 * @param userId
	 */
	public void getUserById(String userId) {
		OrmUser user = ormUserDao.findOne(userId);
		List<OrmOrgUserMap> userRoleMaps = ormOrgUserMapDao.findByUserId(userId);
		List<OrmOrganization> orgs = new ArrayList<OrmOrganization>();
		for (OrmOrgUserMap oum : userRoleMaps) {
			OrmOrganization org = ormOrganizationDao.findOne(oum.getOrgId());
			orgs.add(org);
		}
	}

	public List<OrmUser> getUserList(String userAcct) {
		FilterGroup group = QueryConditionHelper.add(new String[] { "userAcct" }, new String[] { userAcct },
				new String[] { "like" });
		List<OrmUser> list = findAll(group);
		return list;
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void deleteUser(String userId) {
		ormUserDao.delete(userId);
		ormOrgUserMapDao.deleteByUserId(userId);
		ormUserRoleMapDao.deleteByUserId(userId);
	}

	/**
	 * 编辑用户组织机构关联，在更新用户的时候用
	 * 
	 * @param userId
	 * @param orgIds
	 */
	public void editOrgUserMap(String userId, List<String> orgIds) {
		List<OrmOrgUserMap> list = ormOrgUserMapDao.findByUserId(userId);
		for (OrmOrgUserMap oum : list) {
			String orgId = oum.getId();
			if (orgIds.contains(orgId)) {
				orgIds.remove(orgId);
			} else {
				ormOrgUserMapDao.delete(oum);
				ormUserRoleMapDao.deleteByUserIdAndOrgId(userId, orgId);
			}
		}
		for (String orgId : orgIds) {
			OrmOrgUserMap oum = new OrmOrgUserMap();
			oum.setUserId(userId);
			oum.setOrgId(orgId);
			ormOrgUserMapDao.save(oum);
			List<OrmOrgRoleMap> orgRoleMaps = ormOrgRoleMapDao.findByOrgId(orgId);
			for (OrmOrgRoleMap orm : orgRoleMaps) {
				createUserRoleMap(userId, orm.getRoleId(), orgId, orm.getSystemId());
			}
		}
	}

	/**
	 * 编辑用户和角色之间的关联关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void editUserRoleMap(String userId, List<String> roleIds) {
		List<OrmUserRoleMap> list = ormUserRoleMapDao.findByUserId(userId);
		for(OrmUserRoleMap urm : list){
			String roleId = urm.getRoleId();
			if (roleIds.contains(roleId)) {
				roleIds.remove(roleId);
			} else {
				ormUserRoleMapDao.delete(urm);
			}
		}
		for (String roleId : roleIds) {
			OrmRole role = ormRoleDao.findOne(roleId);
			createUserRoleMap(userId, role.getRoleId(), null, role.getSystemId());
		}
	}

	/**
	 * 创建用户和角色关联
	 * 
	 * @param userId
	 * @param role
	 * @param orgId
	 * @return
	 */
	public void createUserRoleMap(String userId, String roleId, String orgId, String systemId) {
		OrmUserRoleMap urm = new OrmUserRoleMap();
		urm.setUserId(userId);
		if (orgId == null) {
			urm.setMapType("USER_TO_ROLE");
		} else {
			urm.setOrgId(orgId);
			urm.setMapType("USER_ORG_TO_ROLE");
		}
		urm.setRoleId(roleId);
		urm.setSystemId(systemId);
		ormUserRoleMapDao.save(urm);
	}
}
