package cn.innosoft.fw.orm.server.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.TreeNode;
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
	
	@RequestMapping("/forward/manage")
	public String forwardManage(Model model,String orgId){
		model.addAttribute("orgId", orgId);
		return "orm/org/org/orgManager";
	}
	@RequestMapping("/forward/role/assign/{orgId}")
	public String forwardOrgRoleAssign(Model model,@PathVariable String orgId){
		model.addAttribute("id", orgId);
		model.addAttribute("type", "org");
		return "orm/org/org/roleAssign";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public void addOrg(OrmOrganization org){
		ormOrganizationService.addOrganization(org);
	}
	@RequestMapping("/delete")
	@ResponseBody
	public void deleteOrg(String orgId){
		ormOrganizationService.deleteOrganization(orgId);
	}
	@RequestMapping("/update")
	@ResponseBody
	public void updateOrg(OrmOrganization org,String[] enforceUpdateField){
		ormOrganizationService.updateSome(org,Arrays.asList(enforceUpdateField));
	}
	@RequestMapping("/tree")
	@ResponseBody
	public List<TreeNode> getInsititutionTree(){
		return ormOrganizationService.createInstitutionTree();
	}
	@RequestMapping("/tree/{orgId}")
	@ResponseBody
	public List<TreeNode> getOrgTree(@PathVariable String orgId){
		return ormOrganizationService.createOrgTreeByInstitution(orgId);
	}
	@RequestMapping("/role/assign")
	@ResponseBody
	public List<Map<String, Object>> getOrgAssignRole(String orgId,String roleName,String systemId){
		return ormRoleService.getOrgAssignRole(orgId, roleName, systemId);
	}
	@RequestMapping("/role/notassign")
	@ResponseBody
	public List<Map<String, Object>> getOrgNotAssignRole(String orgId,String roleName,String systemId){
		return ormRoleService.getOrgNotAssignRole(orgId, roleName, systemId);
	}
	@RequestMapping("/role/add/{orgId}/{roleId}/{systemId}")
	@ResponseBody
	public void addRoleToOrg(@PathVariable String orgId,@PathVariable String roleId,@PathVariable String systemId){
		ormOrganizationService.createOrgRoleMap(orgId, roleId, systemId);
	}
	@RequestMapping("/role/delete/{orgId}/{roleId}")
	@ResponseBody
	public void deleteRoleFromOrg(@PathVariable String orgId,@PathVariable String roleId){
		ormOrganizationService.deleteOrgRoleMap(orgId, roleId);
	}
	
	
	@RequestMapping("/forward/user/role/assign/{userId}")
	public String forwardUserRoleAssign(Model model,@PathVariable String userId){
		model.addAttribute("id", userId);
		model.addAttribute("type", "user");
		return "orm/org/org/roleAssign";
	}
	@RequestMapping("/user/list/{orgId}")
	@ResponseBody
	public List<OrmUser> getUserByOrgId(@PathVariable String orgId){
		return ormUserService.getUserByOrgId(orgId);
	}
	@RequestMapping("/user/add")
	@ResponseBody
	public void addUserToPost(String userId,String orgId){
		ormOrganizationService.addUserToOrg(userId, orgId);
	}
	@RequestMapping("/user/delete")
	@ResponseBody
	public void deleteUserFromOrg(String userId,String orgId){
		ormOrganizationService.deleteUserFromOrg(userId, orgId);
	}
	@RequestMapping("/user/associate")
	@ResponseBody
	public List<OrmUser> userAssociate(String nameOrAcct){
		return ormUserService.userAssociate(nameOrAcct);
	}
	
	@RequestMapping("/user/update")
	@ResponseBody
	public void updateUserInfo(OrmUser user,String[] enforceUpdateField){
		ormUserService.updateSome(user,Arrays.asList(enforceUpdateField));
	}
	@RequestMapping("/user/postUpdate/{userId}/{orgId}")
	@ResponseBody
	public void updateUserPost(@PathVariable String userId,@PathVariable String orgId){
		ormUserService.editOrgUserMap(userId, orgId);
	}
	@RequestMapping("/user/role/assign")
	@ResponseBody
	public List<Map<String, Object>> getUserAssignRole(String userId,String roleName,String systemId){
		return ormRoleService.getUserAssignRole(userId, roleName, systemId);
	}
	@RequestMapping("/user/role/notassign")
	@ResponseBody
	public List<Map<String, Object>> getUserNotAssignRole(String userId,String roleName,String systemId){
		return ormRoleService.getUserNotAssignRole(userId, roleName, systemId);
	}
	@RequestMapping("/user/role/add/{userId}/{roleId}/{systemId}")
	@ResponseBody
	public void addRoleToUser(@PathVariable String userId,@PathVariable String roleId,@PathVariable String systemId){
		ormUserService.createUserRoleMap(userId, roleId, null, systemId);
	}
	@RequestMapping("/user/role/delete/{userId}/{roleId}")
	@ResponseBody
	public void addRoleFromUser(@PathVariable String userId,@PathVariable String roleId){
		ormUserService.deletUserRoleMap(userId, roleId);
	}
	@RequestMapping("/user/detail/{userId}")
	@ResponseBody
	public Map<String, Object> getFullUserInfo(@PathVariable String userId){
		return ormUserService.getFullUserInfo(userId);
	}
}
