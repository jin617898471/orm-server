package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.SelectTreeBean;
import cn.innosoft.fw.orm.server.model.TreeNode;
import cn.innosoft.fw.orm.server.service.OrmOrganizationService;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value = "user")
public class OrmUserResource {
	private static final Logger logger = LogManager.getLogger(OrmUserResource.class);
	@Autowired
	private OrmUserService ormUserService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	
	/**
	 * 跳转到用户管理界面
	 * @return
	 */
	@RequestMapping("/manager")
	public String forwardUserManager() {
		return "orm/org/user/ormUserManage";
	}
	
	/**
	 * 按查询条件返回用户集分页
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody 
	public PageResponse<Map<String, Object>> listUsers(PageRequest pageRequest){
		return ormUserService.find(pageRequest);
	}
	/**
	 * 跳转到，用户新增，查看，编辑和修改密码界面
	 * @param model
	 * @param sign
	 * @param userId
	 * @return
	 */
	@RequestMapping("forward/{sign}/{userId}")
	public String forwardUserDetail(Model model,@PathVariable String sign, @PathVariable String userId){
		if(!"add".equals(sign)){
			model.addAttribute("OrmUser", ormUserService.getUserById(userId));
		}
		model.addAttribute("sign", sign);
		if("changePwd".equals(sign)){
			return "orm/org/user/changePwd";
		}
		return "orm/org/user/ormUserADE";
	}
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	@RequestMapping("/changePwd")
	@ResponseBody
	public String changePwd(OrmUser user){
		try {
			ormUserService.updateSome(user);
			return "true";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "false";
		}
	}
	
	/**
	 * 编辑用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String editUser(OrmUser user){
		return ormUserService.updateUser(user);
	}
	/**
	 * 新增用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addUser(OrmUser user){
		return ormUserService.addUser(user);
	}
	/**
	 * 核实用户账号唯一性
	 * @param userAcct
	 * @return
	 */
	@RequestMapping("/checkUserAcct")
	@ResponseBody
	public String checkUnique(String userAcct){
		return ormUserService.checkUserUniqueness(userAcct);
	}
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void deleteAction(@PathVariable String id) {
		ormUserService.delete(id);
	}
	@RequestMapping("/userAssociate")
	@ResponseBody
	public List<OrmUser> userAssociate(String userAcct){
		return ormUserService.userAssociate(userAcct);
	}
	/**
	 * 批量删除
	 * 
	 * @param idArray
	 */
	@RequestMapping("/deletebatch/{idArray}")
	@ResponseBody
	public void deletebatchAction(@PathVariable ArrayList<String> idArray) {
		ormUserService.deleteBatch(idArray);
	}
	/**
	 * 生成组织机构下拉树
	 * @return
	 */
	@RequestMapping("/selectTreeOrg")
	@ResponseBody
	public List<SelectTreeBean> getOrgSelectTree(){
		return ormOrganizationService.createSelectTree();
	}
	
	@RequestMapping("/testFullUserInfo")
	@ResponseBody
	public Map<String, Object> testFullUserInfo(String userId){
		return ormUserService.getFullUserInfo(userId);
	}
	@RequestMapping("/testgetUserByOrgId")
	@ResponseBody
	public List<OrmUser> getUserByOrgId(String orgId){
		return ormUserService.getUserByOrgId(orgId);
	}
	@RequestMapping("/testInstitutionTree")
	@ResponseBody
	public List<TreeNode> getInstitutionTree(){
		return ormOrganizationService.createInstitutionTree();
	}
	@RequestMapping("/testOrgTree")
	@ResponseBody
	public List<TreeNode> getOrgTree(String instId){
		return ormOrganizationService.createOrgTreeByInstitution(instId);
	}
}
