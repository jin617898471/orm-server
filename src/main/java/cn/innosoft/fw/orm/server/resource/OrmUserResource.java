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
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping("role/add")
	@ResponseBody
	public InfoWrap orgRoleAdd(String userId, String roles) {
		try {
			ormUserService.addRoles(userId, roles);
			return Result.generateSuccessWithoutData("新增角色成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "新增角色失败");
		}
	}

	@RequestMapping("role/delete")
	@ResponseBody
	public InfoWrap orgRoleDelete(String userId, @RequestParam(value = "roles[]") String[] roles) {
		try {
			ormUserService.deleteRoles(userId, roles);
			return Result.generateSuccessWithoutData("新增角色成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "新增角色失败");
		}
	}
}
