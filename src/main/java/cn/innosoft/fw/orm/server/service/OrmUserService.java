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
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.OrmUserRoleMap;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

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
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	@Autowired
	private OrmRoleService ormRoleService;

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
	public PageResponse<Map<String, Object>> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		Map<String, Object> args = pageRequest.getFilterGroup().getMap();
		String arg = (String) args.get("userAcct");
		if(arg != null){
			args.put("userAcct", "%"+arg+"%");
		}
		arg = (String) args.get("userName");
		if(arg != null){
			args.put("userName", "%"+arg+"%");
		}
		arg = (String) args.get("userMobile");
		if(arg != null){
			args.put("userMobile", "%"+arg+"%");
		}
		arg = (String) args.get("orgId");
		if(arg != null && arg.indexOf(",") > 0){
			String[] orgIds = arg.split(",");
			List<String> list = new ArrayList<String>();
			for(String orgId : orgIds){
				list.add(orgId);
			}
			args.put("orgId", list);
		}
		PageResponse<Map<String, Object>> page = findMapBySql("userService-getUser", args, pageRequest);
		return page;
	}
	public List<OrmUser> getUserByOrgId(String orgId){
		return ormUserDao.getUserByOrgId(orgId);
	}
	/**
	 * 添加用户
	 * 
	 * @param ormUser
	 * @return
	 */
	public String addUser(OrmUser ormUser) {
		try {
			ormUser= ormUserDao.save(ormUser);
			editOrgUserMap(ormUser.getUserId(), ormUser.getOrgIds());
			return "true";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "false";
		}
	}

	/**
	 * 检查用户账号唯一性
	 * 
	 * @param ormUser
	 * @return
	 */
	public String checkUserUniqueness(String userAcct) {
		List<OrmUser> users = ormUserDao.findByUserAcct(userAcct);
		if(users.size() > 0){
			return "true";
		}
		return "false";
	}

	/**
	 * 更新用户信息
	 * 
	 * @param ormUser
	 * @param orgIds
	 * @return
	 */
	public String updateUser(OrmUser ormUser) {
		try {
			updateSome(ormUser);
			String strOIds = ormUser.getOrgIds();
			editOrgUserMap(ormUser.getUserId(), strOIds);
			return "true";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "false";
		}
	}

	/**
	 * 查询单个账户
	 * @param userId
	 * @return
	 */
	public OrmUser getUserById(String userId) {
		OrmUser user = ormUserDao.findOne(userId);
		List<OrmOrgUserMap> oums = ormOrgUserMapDao.findByUserId(userId);
		String orgNames = "";
		for(int i=0;i<oums.size();i++){
			String name = oums.get(i).getOrgId();
			if(i != oums.size()-1){
				orgNames += name + ",";
			}else{
				orgNames += name;
			}
		}
		user.setOrgIds(orgNames);
		return user;
	}
	
	public Map<String, Object> getFullUserInfo(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		OrmUser user = ormUserDao.findOne(userId);
		map.put("user", user);
		map.put("org", ormOrganizationService.getOrgByUserId(userId));
		List<Object[]> list = ormRoleService.findRoleInfoByUserId(userId);
		List<OrmRole> roles = new ArrayList<OrmRole>();
		for(Object[] objs : list){
			OrmRole role = (OrmRole) objs[1];
			role.setMapType((String)objs[0]);
			roles.add(role);
		}
		map.put("role", roles);
		return map;
	}
	/**
	 * 联想功能
	 * @param userAcct
	 * @return
	 */
	public List<OrmUser> userAssociate(String userAcct) {
		userAcct = "%" + userAcct + "%";
		List<OrmUser> list = ormUserDao.findFirst10ByUserAcctLikeOrUserNameLikeOrderByCreateDt(userAcct,userAcct);
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
	 * 批量删除
	 * @param userIds
	 */
	public void deleteBatch(List<String> userIds){
		ormUserDao.deleteByUserIdIn(userIds);
		ormOrgUserMapDao.deleteByUserIdIn(userIds);
		ormUserRoleMapDao.deleteByUserIdIn(userIds);
	}
	/**
	 * 编辑用户组织机构关联
	 * 
	 * @param userId
	 * @param orgIds
	 */
	public void editOrgUserMap(String userId, String strOIds) {
		List<String> orgIds = new ArrayList<String>();
		String[] temp = strOIds.split(",");
		for(String oId : temp){
			orgIds.add(oId);
		}
		List<OrmOrgUserMap> list = ormOrgUserMapDao.findByUserId(userId);
		for (OrmOrgUserMap oum : list) {
			String orgId = oum.getOrgId();
			if (orgIds.contains(orgId)) {
				orgIds.remove(orgId);
			} else {
				ormOrgUserMapDao.delete(oum.getId());
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
	public void deleteOrgUserMap(String userId,String orgId){
		ormOrgUserMapDao.deleteByUserIdAndOrgId(userId, orgId);
		ormUserRoleMapDao.deleteByUserIdAndOrgId(userId, orgId);
	}
	/**
	 * 删除用户和角色之间的关联关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void deletUserRoleMap(String userId, String roleId) {
		ormUserRoleMapDao.deleteByUserIdAndRoleId(userId, roleId);
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
