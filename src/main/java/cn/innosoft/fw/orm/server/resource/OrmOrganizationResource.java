package cn.innosoft.fw.orm.server.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.service.OrmOrganizationService;
import cn.innosoft.fw.orm.server.service.OrmRoleService;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value="org")
public class OrmOrganizationResource {
	
	@Autowired
	private OrmUserService ormUserService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	@Autowired
	private OrmRoleService ormRoleService;
	
	private FwLog logger = FwLogFactory.getLog(this.getClass());
	
	@RequestMapping("/institutionTree")
	@ResponseBody
	public InfoWrap getInstitutionTree() {
		try {
			List<ZtreeBean> data = ormOrganizationService.getInstitutionTree();
			return Result.generateSuccess("获取机构树成功", data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Result.generateFail("500", "获取组织机构树失败");
		}
	}

	@RequestMapping("info/{orgId}")
	@ResponseBody
	public InfoWrap getOrgInfo(@PathVariable String orgId) {
		try {
			OrmOrganization data = ormOrganizationService.findOne(orgId);
			return Result.generateSuccess("获取组织机构信息成功", data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Result.generateFail("500", "获取组织机构信息失败");
		}
	}

	@RequestMapping("institution/children")
	@ResponseBody
	public PageResponse<OrmOrganization> getInstChildren(PageRequest pageRequest) {
		try {
			PageResponse<OrmOrganization> data = ormOrganizationService.findAll(pageRequest);
			System.out.println(data);
			return data;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public InfoWrap updateOrg(OrmOrganization org, HttpServletRequest request) {
		try {
			String[] fields = new String[]{"orgName","orgNameShort","orgCode","orgArea",
					"orgPhone","orgLinkman","orgEmail","orgWeburl","orgPostcode","orgAddress"};
			ormOrganizationService.updateSome(org, Arrays.asList(fields));
			return Result.generateSuccessWithoutData("保存成功");
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Result.generateFail("500", "保存失败");
		}
	}

	@RequestMapping("delete/{orgId}")
	@ResponseBody
	public InfoWrap deleteOrg(@PathVariable String orgId) {
		try {
			return Result.generateSuccessWithoutData("删除成功");
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Result.generateFail("500", "删除失败");
		}
	}

	@RequestMapping("institution/children/{orgId}")
	@ResponseBody
	public InfoWrap getNodeChildren(@PathVariable String orgId) {
		try {
			List<ZtreeBean> data = ormOrganizationService.getNodeChildren(orgId);
			return Result.generateSuccess("获取子节点成功", data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Result.generateFail("500", "获取子节点失败");
		}
	}

	@RequestMapping("institution/forward/add/{pId}")
	public String instAddForward(Model model, @PathVariable String pId) {
		model.addAttribute("parentOrgId", pId);
		model.addAttribute("sign", "add");
		return "orm/frame/dialog-subadd";
	}
	@RequestMapping("add")
	@ResponseBody
	public InfoWrap addOrg(OrmOrganization org, HttpServletRequest request) {
		try {
			ormOrganizationService.add(org);
			return Result.generateSuccessWithoutData("保存成功");
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Result.generateFail("500", "保存失败");
		}
	}

	@RequestMapping("institution/forward/edit/{id}")
	public String instEditForward(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("org", org);
		model.addAttribute("sign", "edit");
		return "orm/frame/dialog-subadd";
	}
	// @RequestMapping("/forward/manage")
	// public String forwardManage(Model model, String orgId) {
	// model.addAttribute("orgId", orgId);
	// return "orm/org/org/orgManager";
	// }
	//
	// @RequestMapping("/forward/role/assign/{orgId}")
	// public String forwardOrgRoleAssign(Model model, @PathVariable String
	// orgId) {
	// model.addAttribute("id", orgId);
	// model.addAttribute("type", "org");
	// return "orm/org/org/roleAssign";
	// }
	//
	// @RequestMapping("/add")
	// @ResponseBody
	// public void addOrg(OrmOrganization org) {
	// log.info("新增组织机构信息");
	// ormOrganizationService.addOrganization(org);
	// }
	//
	// @RequestMapping("/delete")
	// @ResponseBody
	// public void deleteOrg(String orgId) {
	// log.info("删除组织机构信息");
	// ormOrganizationService.deleteOrganization(orgId);
	// }
	//
	// @RequestMapping("/update")
	// @ResponseBody
	// public void updateOrg(OrmOrganization org, String[] enforceUpdateField) {
	// log.info("更新组织机构信息");
	// ormOrganizationService.updateOrg(org, enforceUpdateField);
	// }
	//
	// @RequestMapping("/tree")
	// @ResponseBody
	// public List<TreeNode> getInsititutionTree() {
	// return ormOrganizationService.createInstitutionTree();
	// }
	//
	// @RequestMapping("/tree/{orgId}")
	// @ResponseBody
	// public List<TreeNode> getOrgTree(@PathVariable String orgId) {
	// return ormOrganizationService.createOrgTreeByInstitution(orgId);
	// }
	//
	// @RequestMapping("/role/assign")
	// @ResponseBody
	// public List<Map<String, Object>> getOrgAssignRole(String orgId, String
	// roleName, String systemId) {
	// return ormRoleService.getOrgAssignRole(orgId, roleName, systemId);
	// }
	//
	// @RequestMapping("/role/notassign")
	// @ResponseBody
	// public List<Map<String, Object>> getOrgNotAssignRole(String orgId, String
	// roleName, String systemId) {
	// return ormRoleService.getOrgNotAssignRole(orgId, roleName, systemId);
	// }
	//
	// @RequestMapping("/role/add/{orgId}/{roleId}/{systemId}")
	// @ResponseBody
	// public void addRoleToOrg(@PathVariable String orgId, @PathVariable String
	// roleId, @PathVariable String systemId) {
	// log.info("新增岗位的角色");
	// ormOrganizationService.createOrgRoleMap(orgId, roleId, systemId);
	// }
	//
	// @RequestMapping("/role/delete/{orgId}/{roleId}")
	// @ResponseBody
	// public void deleteRoleFromOrg(@PathVariable String orgId, @PathVariable
	// String roleId) {
	// log.info("删除岗位的角色");
	// ormOrganizationService.deleteOrgRoleMap(orgId, roleId);
	// }
	//
	// @RequestMapping("/forward/user/role/assign/{userId}")
	// public String forwardUserRoleAssign(Model model, @PathVariable String
	// userId) {
	// model.addAttribute("id", userId);
	// model.addAttribute("type", "user");
	// return "orm/org/org/roleAssign";
	// }
	//
	// @RequestMapping("/user/list/{orgId}")
	// @ResponseBody
	// public List<OrmUser> getUserByOrgId(@PathVariable String orgId) {
	// return ormUserService.getUserByOrgId(orgId);
	// }
	//
	// @RequestMapping("/user/add")
	// @ResponseBody
	// public String addUserToPost(String userId, String orgId) {
	// log.info("新增岗位下的用户");
	// return ormOrganizationService.addUserToOrg(userId, orgId);
	// }
	//
	// @RequestMapping("/user/delete")
	// @ResponseBody
	// public void deleteUserFromOrg(String userId, String orgId) {
	// log.info("删除岗位下的用户");
	// ormOrganizationService.deleteUserFromOrg(userId, orgId);
	// }
	//
	// @RequestMapping("/user/associate")
	// @ResponseBody
	// public List<OrmUser> userAssociate(String nameOrAcct) {
	// return ormUserService.userAssociate(nameOrAcct);
	// }
	//
	// @RequestMapping("/user/update")
	// @ResponseBody
	// public void updateUserInfo(OrmUser user, String[] enforceUpdateField) {
	// log.info("修改岗位下用户的基本信息");
	// ormUserService.updateSome(user, Arrays.asList(enforceUpdateField));
	// }
	//
	// @RequestMapping("/user/postUpdate/{userId}/{orgId}")
	// @ResponseBody
	// public void updateUserPost(@PathVariable String userId, @PathVariable
	// String orgId) {
	// ormUserService.editOrgUserMap(userId, orgId);
	// }
	//
	// @RequestMapping("/user/role/assign")
	// @ResponseBody
	// public List<Map<String, Object>> getUserAssignRole(String userId, String
	// roleName, String systemId) {
	// return ormRoleService.getUserAssignRole(userId, roleName, systemId);
	// }
	//
	// @RequestMapping("/user/role/notassign")
	// @ResponseBody
	// public List<Map<String, Object>> getUserNotAssignRole(String userId,
	// String roleName, String systemId) {
	// return ormRoleService.getUserNotAssignRole(userId, roleName, systemId);
	// }
	//
	// @RequestMapping("/user/role/add/{userId}/{roleId}/{systemId}")
	// @ResponseBody
	// public void addRoleToUser(@PathVariable String userId, @PathVariable
	// String roleId, @PathVariable String systemId) {
	// log.info("新增用户的角色");
	// ormUserService.createUserRoleMap(userId, roleId, null, systemId);
	// }
	//
	// @RequestMapping("/user/role/delete/{userId}/{roleId}")
	// @ResponseBody
	// public void addRoleFromUser(@PathVariable String userId, @PathVariable
	// String roleId) {
	// log.info("删除用户的角色");
	// ormUserService.deletUserRoleMap(userId, roleId);
	// }
	//
	// @RequestMapping("/user/detail/{userId}")
	// @ResponseBody
	// public Map<String, Object> getFullUserInfo(@PathVariable String userId) {
	// return ormUserService.getFullUserInfo(userId);
	// }
}
