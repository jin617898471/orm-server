package cn.innosoft.fw.orm.server.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.innosoft.fw.orm.server.common.entity.InfoWrap;
import cn.innosoft.fw.orm.server.common.result.Result;
import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmUser;
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
	
	private static final Logger logger = LogManager.getLogger(OrmOrganizationResource.class);
	
	@RequestMapping("/institutionTree")
	@ResponseBody
	public InfoWrap getInstitutionTree() {
		try {
			List<ZtreeBean> data = ormOrganizationService.getInstitutionTree();
			return Result.generateSuccess("获取机构树成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取组织机构信息失败");
		}
	}

	@RequestMapping("children")
	@ResponseBody
	public PageResponse<OrmOrganization> getInstChildren(PageRequest pageRequest) {
		try {
			PageResponse<OrmOrganization> data = ormOrganizationService.findAll(pageRequest);
			return data;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public InfoWrap updateOrg(OrmOrganization org, HttpServletRequest request) {
		try {
			String str = org.getOrgColumns();
			String[] columns = str.split(",");
			// String[] fields = new
			// String[]{"orgName","orgNameShort","orgCode","orgArea",
			// "orgPhone","orgLinkman","orgEmail","orgWeburl","orgPostcode","orgAddress"};
			ormOrganizationService.updateSome(org, Arrays.asList(columns));
			return Result.generateSuccessWithoutData("保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "保存失败");
		}
	}

	@RequestMapping("delete/{orgId}")
	@ResponseBody
	public InfoWrap deleteOrg(@PathVariable String orgId) {
		try {
			ormOrganizationService.deleteOrganization(orgId);
			return Result.generateSuccessWithoutData("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "删除失败");
		}
	}

	@RequestMapping("children/{type}/{orgId}")
	@ResponseBody
	public InfoWrap getNodeChildren(@PathVariable String type, @PathVariable String orgId) {
		try {
			List<ZtreeBean> data = ormOrganizationService.getInstNodeChildren(type, orgId);
			return Result.generateSuccess("获取子节点成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取子节点失败");
		}
	}

	@RequestMapping("institution/forward/add/{pId}")
	public String instAddForward(Model model, @PathVariable String pId) {
		model.addAttribute("parentOrgId", pId);
		model.addAttribute("sign", "add");
		return "orm/org/institution/frame/dialog-subadd";
	}
	@RequestMapping("add")
	@ResponseBody
	public InfoWrap addOrg(OrmOrganization org, HttpServletRequest request) {
		try {
			ormOrganizationService.add(org);
			return Result.generateSuccessWithoutData("保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "保存失败");
		}
	}

	@RequestMapping("institution/forward/edit/{id}")
	public String instEditForward(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("org", org);
		model.addAttribute("sign", "edit");
		return "orm/org/institution/frame/dialog-subadd";
	}

	@RequestMapping("/depTree/{orgId}")
	@ResponseBody
	public InfoWrap getDepTree(@PathVariable String orgId) {
		try {
			List<ZtreeBean> data = ormOrganizationService.getDepTree(orgId, true);
			return Result.generateSuccess("获取部门树成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.generateFail("500", "获取部门树失败");
		}
	}

	@RequestMapping("/depTree/updateNode/{orgId}")
	@ResponseBody
	public InfoWrap updateDepTreeNode(@PathVariable String orgId) {
		try {
			List<ZtreeBean> data = ormOrganizationService.getDepTree(orgId, false);
			return Result.generateSuccess("获取部门树节点成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.generateFail("500", "获取部门树节点失败");
		}
	}

	@RequestMapping("/forward/department/{orgId}")
	public String forwardDepartment(Model model, @PathVariable String orgId) {
		model.addAttribute("orgId", orgId);
		return "orm/org/department/frame/dep-manage";
	}

	@RequestMapping("/role/assign")
	@ResponseBody
	public InfoWrap getOrgAssignRole(String orgId, String roleName, String systemId) {
		try {
			List<Map<String, Object>> data = ormRoleService.getOrgAssignRole(orgId, roleName, systemId);
			return Result.generateSuccess("获取组织机构已分配角色成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取组织机构已分配角色失败");
		}
	}

	@RequestMapping("/role/notassign")
	@ResponseBody
	public InfoWrap getOrgNotAssignRole(String orgId, String roleName, String systemId) {
		try {
			List<Map<String, Object>> data = ormRoleService.getOrgNotAssignRole(orgId, roleName, systemId);
			return Result.generateSuccess("获取组织机构可分配角色成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取组织机构可分配角色失败");
		}
	}

	@RequestMapping("/forward/instTab/{id}")
	public String forwardInstTab(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("orgId", id);
		model.addAttribute("org", org);
		return "orm/org/department/frame/inst-tab";
	}

	@RequestMapping("/forward/depTab/{id}")
	public String forwardDepTab(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("orgId", id);
		model.addAttribute("org", org);
		return "orm/org/department/frame/dep-tab";
	}

	@RequestMapping("/forward/postTab/{id}")
	public String forwardPostTab(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("orgId", id);
		model.addAttribute("org", org);
		return "orm/org/department/frame/post-tab";
	}

	@RequestMapping("/forward/empTab/{id}")
	public String forwardEmpTab(Model model, @PathVariable String id) {
		OrmUser user = ormUserService.findOne(id);
		model.addAttribute("userId", id);
		model.addAttribute("user", user);
		return "orm/org/department/frame/emp-tab";
	}

	@RequestMapping("emps/{orgId}")
	@ResponseBody
	public PageResponse<OrmUser> getOrgUsers(@PathVariable String orgId, PageRequest pageRequest) {
		try {
			PageResponse<OrmUser> data = ormUserService.getOrgUsers(orgId, pageRequest);
			return data;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping("dep/forward/add/{pId}")
	public String depAddForward(Model model, @PathVariable String pId) {
		model.addAttribute("parentOrgId", pId);
		model.addAttribute("sign", "add");
		return "orm/org/department/frame/dep-AE";
	}

	@RequestMapping("post/forward/add/{pId}")
	public String postAddForward(Model model, @PathVariable String pId) {
		model.addAttribute("parentOrgId", pId);
		model.addAttribute("sign", "add");
		return "orm/org/department/frame/post-AE";
	}

	@RequestMapping("emp/forward/add")
	public String empAddForward(Model model) {
		// model.addAttribute("orgId", pId);
		model.addAttribute("sign", "org");
		return "orm/org/department/frame/dialog-addperson";
	}

	@RequestMapping("emp/forward/edit/{userId}")
	public String empEditForward(Model model, @PathVariable String userId) {
		OrmUser user = ormUserService.findOne(userId);
		model.addAttribute("user", user);
		// model.addAttribute("sign", "add");
		return "orm/org/department/frame/dialog-editperson";
	}

	@RequestMapping("dep/forward/edit/{id}")
	public String depEditForward(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("org", org);
		model.addAttribute("sign", "edit");
		return "orm/org/department/frame/dep-AE";
	}

	@RequestMapping("post/forward/edit/{id}")
	public String postEditForward(Model model, @PathVariable String id) {
		OrmOrganization org = ormOrganizationService.findOne(id);
		model.addAttribute("org", org);
		model.addAttribute("sign", "edit");
		return "orm/org/department/frame/post-AE";
	}

	@RequestMapping("institution/options")
	@ResponseBody
	public InfoWrap getInstOptions() {
		try {
			List<Map<String, Object>> data = ormOrganizationService.getInstOptions();
			return Result.generateSuccess("获取机构下拉选项成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取机构下拉选项失败");
		}
	}

	@RequestMapping("institution/children/options/{instId}")
	@ResponseBody
	public InfoWrap getInstChildrenOptions(@PathVariable String instId) {
		try {
			Map<String, Object> data = ormOrganizationService.getInstChildrenOptions(instId);
			return Result.generateSuccess("获取机构下的部门和岗位的下拉选项成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取机构下的部门和岗位的下拉选项失败");
		}
	}

	@RequestMapping("post/options/{depId}")
	@ResponseBody
	public InfoWrap getPostOptions(@PathVariable String depId) {
		try {
			List<Map<String, Object>> data = ormOrganizationService.getPostOptions(depId);
			return Result.generateSuccess("获取机构下拉选项成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取机构下拉选项失败");
		}
	}

	@RequestMapping("code/ztree/{orgId}")
	@ResponseBody
	public InfoWrap getOrgCodeTree(@PathVariable String orgId) {
		try {
			List<ZtreeBean> data = ormOrganizationService.generateOrgCodeTree(orgId);
			return Result.generateSuccess("获取部门代码树成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取部门代码树失败");
		}
	}

	@RequestMapping("role/ztree/{orgId}")
	@ResponseBody
	public InfoWrap getOrgRoleTree(@PathVariable String orgId) {
		try {
			List<ZtreeBean> data = ormOrganizationService.getOrgRight(orgId);
			return Result.generateSuccess("获取岗位权限树成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取岗位权限树失败");
		}
	}

	@RequestMapping("role/add")
	@ResponseBody
	public InfoWrap orgRoleAdd(String orgId, String roles) {
		try {
			ormOrganizationService.addRoles(orgId, roles);
			return Result.generateSuccessWithoutData("新增角色成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "新增角色失败");
		}
	}

	@RequestMapping("role/delete")
	@ResponseBody
	public InfoWrap orgRoleDelete(String orgId, @RequestParam(value = "roles[]") String[] roles) {
		try {
			// System.out.println(roles.length);
			ormOrganizationService.deleteRoles(orgId, roles);
			return Result.generateSuccessWithoutData("新增角色成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "新增角色失败");
		}
	}
}
