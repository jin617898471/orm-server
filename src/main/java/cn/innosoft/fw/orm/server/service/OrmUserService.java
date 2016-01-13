package cn.innosoft.fw.orm.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.OrmUserRoleMap;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

/**
 * 
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
	@Autowired
	private OrmSystemService ormSystemService;


	@Override
	public BaseDao<OrmUser, String> getBaseDao() {
		return ormUserDao;
	}

	public List<OrmUser> getUserByIds(List<String> userIds) {
		return ormUserDao.findByUserIdIn(userIds);
	}

	public PageResponse<OrmUser> getOrgUsers(String orgId, PageRequest pageRequest) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("orgId", orgId);
		PageResponse<OrmUser> data = ormUserDao.findEntityBySql("org-emps", args, pageRequest);
		return data;
	}

	@SuppressWarnings("unchecked")
	public void addUser(OrmUser user, String orgs) throws JsonParseException, JsonMappingException, IOException {
		OrmUser u = ormUserDao.add(user);
		String userId = u.getUserId();
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> list = mapper.readValue(orgs, List.class);
		for (Map<String, Object> map : list) {
			String institutionId = (String) map.get("institution");
			String departmentId = (String) map.get("department");
			String postId = (String) map.get("post");
			String orgId = !"".equals(postId) ? postId : !"".equals(departmentId) ? departmentId : institutionId;
			OrmOrgUserMap oou = new OrmOrgUserMap();
			oou.setOrgId(orgId);
			oou.setUserId(userId);
			ormOrgUserMapDao.add(oou);
		}
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
	 * 查询用户
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<Map<String, Object>> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		Map<String, Object> args = pageRequest.getFilterGroup().getMap();
		args.put("orgId", "113");
		String arg = (String) args.get("userAcct");
		if(arg != null){
			args.put("userAcct", "%"+arg+"%");
		}
		arg = (String) args.get("userName");
		if(arg != null){
			args.put("userName", "%"+arg+"%");
		}
		arg = (String) args.get("userStatus");
		if(arg != null){
			args.put("userStatus", arg);
		}
		PageResponse<Map<String, Object>> page = findMapBySql("userService-getUser", args, pageRequest);
		return page;
	}

	public void cancelUser(String userId) {
		OrmUser user = findOne(userId);
		user.setUserStatus("注销");
		update(user);
	}

	public void activeUser(String userId) {
		OrmUser user = findOne(userId);
		user.setUserStatus("正常");
		update(user);
	}

	public List<OrmUser> getUserByOrgId(String orgId){
		return ormUserDao.getUserByOrgId(orgId);
	}

	public void changePwd(String userId, String userPwd) {
		OrmUser user = findOne(userId);
		user.setUserPwd(userPwd);
		update(user);
	}

	public List<ZtreeBean> getUserRoles(String userId){
		return ormRoleService.findUserResourceTrees(userId);
	}

	@SuppressWarnings("unchecked")
	public void addRoles(String userId, String roles) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> list = mapper.readValue(roles, List.class);
		for (Map<String, String> map : list) {
			OrmUserRoleMap our = new OrmUserRoleMap();
			our.setUserId(userId);
			our.setRoleId(map.get("roleId"));
			our.setSystemId(map.get("systemId"));
			ormUserRoleMapDao.add(our);
		}
	}

	public void deleteRoles(String userId, String[] roles) {
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(roles));
		ormUserRoleMapDao.deleteByUserIdAndRoleIdIn(userId, list);
	}
}
