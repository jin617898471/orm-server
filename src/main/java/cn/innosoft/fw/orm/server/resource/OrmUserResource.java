package cn.innosoft.fw.orm.server.resource;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.log.FwLog;
import cn.innosoft.fw.biz.log.FwLogFactory;
import cn.innosoft.fw.orm.server.common.entity.InfoWrap;
import cn.innosoft.fw.orm.server.common.result.Result;
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
	public InfoWrap getOrgAssignRole(String orgId, String roleName, String systemId) {
		try {
			List<Map<String, Object>> data = ormRoleService.getUserAssignRole(orgId, roleName, systemId);
			return Result.generateSuccess("获取用户已分配角色成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取用户已分配角色失败");
		}
	}

	@RequestMapping("/role/notassign")
	@ResponseBody
	public InfoWrap getOrgNotAssignRole(String orgId, String roleName, String systemId) {
		try {
			List<Map<String, Object>> data = ormRoleService.getUserNotAssignRole(orgId, roleName, systemId);
			return Result.generateSuccess("获取用户可分配角色成功", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Result.generateFail("500", "获取用户可分配角色失败");
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
