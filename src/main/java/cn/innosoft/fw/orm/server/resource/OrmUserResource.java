package cn.innosoft.fw.orm.server.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.log.FwLog;
import cn.innosoft.fw.biz.log.FwLogFactory;
import cn.innosoft.fw.orm.server.common.entity.InfoWrap;
import cn.innosoft.fw.orm.server.common.result.Result;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.service.OrmOrganizationService;
import cn.innosoft.fw.orm.server.service.OrmRoleService;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value = "user")
public class OrmUserResource {
	@Autowired
	private OrmUserService ormUserService;
	@Autowired
	private OrmRoleService ormRoleService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	
	private FwLog log = FwLogFactory.getLog(this.getClass());
	
	private static final Logger logger = LogManager.getLogger(OrmUserResource.class);

	@RequestMapping("/role/assign")
	@ResponseBody
	public InfoWrap getOrgAssignRole(String userId, String roleName, String systemId) {
		try {
			List<Map<String, Object>> data = ormRoleService.getUserAssignRole(userId, roleName, systemId);
			return Result.generateSuccess("获取用户已分配角色成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取用户已分配角色失败");
		}
	}

	@RequestMapping("/role/notassign")
	@ResponseBody
	public InfoWrap getOrgNotAssignRole(String userId, String roleName, String systemId) {
		try {
			List<Map<String, Object>> data = ormRoleService.getUserNotAssignRole(userId, roleName, systemId);
			return Result.generateSuccess("获取用户可分配角色成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取用户可分配角色失败");
		}
	}

	@RequestMapping("add")
	@ResponseBody
	public InfoWrap addUser(OrmUser user, String orgs) {
		try {
			ormUserService.addUser(user, orgs);
			
			return Result.generateSuccessWithoutData("添加用户成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "添加用户失败");
		}
	}

	@RequestMapping("delete/{userId}")
	@ResponseBody
	public InfoWrap deleteUser(@PathVariable String userId) {
		try {
			ormUserService.deleteUser(userId);
			return Result.generateSuccessWithoutData("删除用户成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "删除用户失败");
		}
	}

	@RequestMapping("edit")
	@ResponseBody
	public InfoWrap deleteUser(OrmUser user,String userColumns) {
		try {
			String[] columns = userColumns.split(",");
			ormUserService.updateSome(user, Arrays.asList(columns));
			return Result.generateSuccessWithoutData("删除用户成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "删除用户失败");
		}
	}

	@RequestMapping("forward/manage")
	public String forwardManage() {
		return "orm/org/user/frame/user-manage";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageResponse<Map<String, Object>> userList(PageRequest pageRequest) {
		try {
			PageResponse<Map<String, Object>> data = ormUserService.find(pageRequest);
			return data;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping("forward/add")
	public String empAddForward(Model model) {
		// model.addAttribute("orgId", pId);
		model.addAttribute("sign", "user");
		return "orm/org/department/frame/dialog-addperson";
	}

	@RequestMapping("forward/edit/{userId}")
	public String empEditForward(Model model, @PathVariable String userId) {
		OrmUser user = ormUserService.findOne(userId);
		model.addAttribute("user", user);
		// model.addAttribute("sign", "add");
		return "orm/org/department/frame/dialog-editperson";
	}

	@RequestMapping("forward/changepwd/{userId}")
	public String changePwdForward(Model model, @PathVariable String userId) {
		// OrmUser user = ormUserService.findOne(userId);
		// model.addAttribute("user", user);
		model.addAttribute("userId", userId);
		return "orm/org/user/frame/changePwd";
	}

	@RequestMapping("cancel/{userId}")
	@ResponseBody
	public InfoWrap cancelUser(@PathVariable String userId) {
		try {
			ormUserService.cancelUser(userId);
			return Result.generateSuccessWithoutData("注销用户成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "注销用户失败");
		}
	}

	@RequestMapping("active/{userId}")
	@ResponseBody
	public InfoWrap activeUser(@PathVariable String userId) {
		try {
			ormUserService.activeUser(userId);
			return Result.generateSuccessWithoutData("激活用户成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "激活用户失败");
		}
	}

	@RequestMapping("changePwd")
	@ResponseBody
	public InfoWrap changePwd(String userId, String userPwd) {
		try {
			ormUserService.changePwd(userId, userPwd);
			return Result.generateSuccessWithoutData("修改密码成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "修改密码失败");
		}
	}

	@RequestMapping("role/ztree/{userId}")
	@ResponseBody
	public InfoWrap getOrgRoleTree(@PathVariable String userId) {
		try {
			List<ZtreeBean> data = ormUserService.getUserRoles(userId);
			return Result.generateSuccess("获取岗位权限树成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取岗位权限树失败");
		}
	}
	// /**
	// * 跳转到用户管理界面
	// * @return
	// */
	// @RequestMapping("/forward/manage")
	// public String forwardUserManager() {
	// return "orm/org/user/ormUserManage";
	// }
	//
	// /**
	// * 按查询条件返回用户集分页
	// * @param pageRequest
	// * @return
	// */
	// @RequestMapping("/listPage")
	// @ResponseBody
	// public PageResponse<Map<String, Object>> listUsers(PageRequest
	// pageRequest){
	// return ormUserService.find(pageRequest);
	// }
	// /**
	// * 跳转到，用户新增，查看，编辑和修改密码界面
	// * @param model
	// * @param sign
	// * @param userId
	// * @return
	// */
	// @RequestMapping("forward/{sign}/{userId}")
	// public String forwardUserDetail(Model model,@PathVariable String sign,
	// @PathVariable String userId){
	// if(!"add".equals(sign)){
	// model.addAttribute("OrmUser", ormUserService.getUserById(userId));
	// }
	// model.addAttribute("sign", sign);
	// if("changePwd".equals(sign)){
	// return "orm/org/user/changePwd";
	// }
	// return "orm/org/user/ormUserADE";
	// }
	// /**
	// * 修改密码
	// * @param user
	// * @return
	// */
	// @RequestMapping("/changePwd")
	// @ResponseBody
	// public String changePwd(OrmUser user){
	// log.info("修改用户密码");
	// return ormUserService.ChangePwd(user);
	// }
	//
	// /**
	// * 编辑用户信息
	// * @param user
	// * @return
	// */
	// @RequestMapping("/edit")
	// @ResponseBody
	// public String editUser(OrmUser user){
	// log.info("修改用户基本信息");
	// return ormUserService.updateUser(user);
	// }
	// /**
	// * 新增用户信息
	// * @param user
	// * @return
	// */
	// @RequestMapping("/add")
	// @ResponseBody
	// public String addUser(OrmUser user){
	// log.info("新增用户");
	// return ormUserService.addUser(user);
	// }
	// /**
	// * 核实用户账号唯一性
	// * @param userAcct
	// * @return
	// */
	// @RequestMapping("/checkUserAcct")
	// @ResponseBody
	// public String checkUnique(String userAcct){
	// return ormUserService.checkUserUniqueness(userAcct);
	// }
	// /**
	// * 删除
	// *
	// * @param id
	// */
	// @RequestMapping("/delete/{id}")
	// @ResponseBody
	// public void deleteAction(@PathVariable String id) {
	// log.info("删除用户");
	// ormUserService.deleteUser(id);
	// }
	//
	//
	// /**
	// * 批量删除
	// *
	// * @param idArray
	// */
	// @RequestMapping("/deletebatch/{idArray}")
	// @ResponseBody
	// public void deletebatchAction(@PathVariable ArrayList<String> idArray) {
	// log.info("批量用户");
	// ormUserService.deleteBatch(idArray);
	// }
	// /**
	// * 生成组织机构下拉树
	// * @return
	// */
	// @RequestMapping("/selectTreeOrg")
	// @ResponseBody
	// public List<SelectTreeBean> getOrgSelectTree(){
	// return ormOrganizationService.createSelectTree();
	// }
	//
	// @RequestMapping("/testFullUserInfo")
	// @ResponseBody
	// public Map<String, Object> testFullUserInfo(String userId){
	// return ormUserService.getFullUserInfo(userId);
	// }
	// @RequestMapping("/testgetUserByOrgId")
	// @ResponseBody
	// public List<OrmUser> getUserByOrgId(String orgId){
	// return ormUserService.getUserByOrgId(orgId);
	// }
	// @RequestMapping("/testInstitutionTree")
	// @ResponseBody
	// public List<TreeNode> getInstitutionTree(){
	// return ormOrganizationService.createInstitutionTree();
	// }
	// @RequestMapping("/testOrgTree")
	// @ResponseBody
	// public List<TreeNode> getOrgTree(String instId){
	// return ormOrganizationService.createOrgTreeByInstitution(instId);
	// }
}
