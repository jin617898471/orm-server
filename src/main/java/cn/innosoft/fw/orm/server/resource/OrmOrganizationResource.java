package cn.innosoft.fw.orm.server.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.orm.server.model.OrmOrganization;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.TreeNode;
import cn.innosoft.fw.orm.server.service.OrmOrganizationService;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value="org")
public class OrmOrganizationResource {
	
	@Autowired
	private OrmUserService ormUserService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	
	@RequestMapping("/forward/manage")
	public String forwardManage(){
		return "/org/org/orgManager";
	}
	@RequestMapping("/forward/role/assign/{orgId}")
	public String forwardOrgRoleAssign(Model model,String orgId){
		model.addAttribute("orgId", orgId);
		return "/org/org/orgManager";
	}
	
	@RequestMapping("/add")
	public void addOrg(OrmOrganization org){
		ormOrganizationService.addOrganization(org);
	}
	@RequestMapping("/delete")
	public void deleteOrg(String orgId){
		ormOrganizationService.deleteOrganization(orgId);
	}
	@RequestMapping("/update")
	public void updateOrg(OrmOrganization org){
		ormOrganizationService.updateSome(org);
	}
	@RequestMapping("/tree")
	@ResponseBody
	public List<TreeNode> getInsititutionTree(){
		return ormOrganizationService.createInstitutionTree();
	}
	@RequestMapping("/tree/{orgId}")
	@ResponseBody
	public List<TreeNode> getOrgTree(String orgId){
		return ormOrganizationService.createOrgTreeByInstitution(orgId);
	}
	@RequestMapping("/role/assign/{userId}")
	@ResponseBody
	public List<OrmRole> getOrgAssignRole(String userId){
		return null;
	}
	@RequestMapping("/role/notassign/{userId}")
	@ResponseBody
	public List<OrmRole> getOrgNotAssignRole(String userId){
		return null;
	}
	@RequestMapping("/role/add/{orgId}/{roleId}/{systemId}")
	public void addRoleToOrg(String orgId,String roleId,String systemId){
		ormOrganizationService.createOrgRoleMap(orgId, roleId, systemId);
	}
	@RequestMapping("/role/delete/{orgId}/{roleId}")
	public void deleteRoleFromOrg(String orgId,String roleId){
		ormOrganizationService.deleteOrgRoleMap(orgId, roleId);
	}
	
	
	@RequestMapping("/forward/user/role/assign/{orgId}")
	public String forwardUserRoleAssign(Model model,String userId){
		model.addAttribute("orgId", userId);
		return "/org/org/orgManager";
	}
	@RequestMapping("/user/list/{orgId}")
	@ResponseBody
	public List<OrmUser> getUserByOrgId(String orgId){
		return ormUserService.getUserByOrgId(orgId);
	}
	@RequestMapping("/user/add/")
	public void addUserToPost(String userId,String orgId){
		ormOrganizationService.addUserToOrg(userId, orgId);
	}
	@RequestMapping("/user/delete/")
	public void deleteUserFromOrg(String userId,String orgId){
		ormOrganizationService.deleteUserFromOrg(userId, orgId);
	}
	@RequestMapping("/user/associate/{nameOrAcct}")
	@ResponseBody
	public List<OrmUser> userAssociate(String nameOrAcct){
		return ormUserService.userAssociate(nameOrAcct);
	}
	
	@RequestMapping("/user/update")
	public void updateUserInfo(OrmUser user){
		ormUserService.updateSome(user);
	}
	@RequestMapping("/user/postUpdate/{userId}/{orgId}")
	public void updateUserPost(String userId,String orgId){
		ormUserService.editOrgUserMap(userId, orgId);
	}
	@RequestMapping("/user/role/assign/{userId}")
	@ResponseBody
	public List<OrmRole> getUserAssignRole(String userId){
		return null;
	}
	@RequestMapping("/user/role/notassign/{userId}")
	@ResponseBody
	public List<OrmRole> getUserNotAssignRole(String userId){
		return null;
	}
	@RequestMapping("/user/role/add/{userId}/{roleId}/{systemId}")
	public void addRoleToUser(String userId,String roleId,String systemId){
		ormUserService.createUserRoleMap(userId, roleId, null, systemId);
	}
	@RequestMapping("/user/role/delete/{userId}/{roleId}")
	public void addRoleFromUser(String userId,String roleId){
		ormUserService.deletUserRoleMap(userId, roleId);
	}
	@RequestMapping("/user/detail/{userId}")
	@ResponseBody
	public Map<String, Object> getFullUserInfo(String userId){
		return ormUserService.getFullUserInfo(userId);
	}
}
