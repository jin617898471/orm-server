package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.service.OrmRoleService;

@Controller
@RequestMapping(value = "authority/role")
public class OrmRoleResource {
	
	@Autowired
	private OrmRoleService ormRoleService;
	

	@RequestMapping("/forward/manage")
	public String forwardManage(Model model){
		return "/orm/authority/role/roleManage";
	}
	
	@RequestMapping("/forward/authorize")
	public String forwardAuthorize(Model model){
		return "/orm/authority/authorize/authorizeManage";
	}
	
	@RequestMapping("/forward/detail/{roleId}")
	public String forwardDetail(Model model,@PathVariable String roleId){
		OrmRole ormRole = ormRoleService.findOne(roleId);
		model.addAttribute("ormRole",ormRole);
		model.addAttribute("sign","detail");
		return "/orm/authority/role/roleADE";
	}
	
	@RequestMapping("/forward/add")
	public String forwardAdd(Model model){
		model.addAttribute("sign","add");
		return "/orm/authority/role/roleADE";
	}
	
	@RequestMapping("/forward/edit/{roleId}")
	public String forwardEdit(Model model,@PathVariable String roleId){
		OrmRole ormRole = ormRoleService.findOne(roleId);
		model.addAttribute("ormRole",ormRole);
		model.addAttribute("sign","edit");
		return "/orm/authority/role/roleADE";
	}

	
	@RequestMapping("/add")
	@ResponseBody
	public void addRole(OrmRole ormRole){
		ormRoleService.addRole(ormRole);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public void editRole(OrmRole ormRole,String[] updateField){
		ormRoleService.updateRole(ormRole,Arrays.asList(updateField));
	}
	
	@RequestMapping("/delete/{roleId}")
	@ResponseBody
	public void delete(@PathVariable String roleId){
		ormRoleService.deleteRole(roleId);
	}
	
	@RequestMapping("/deletes/{idArray}")
	@ResponseBody
	public void deletes(@PathVariable ArrayList<String> idArray){
		ormRoleService.deleteRole(idArray);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageResponse<OrmRole> list(PageRequest pageRequest){
		return ormRoleService.findAll(pageRequest);
	}
	
	@RequestMapping("/listnopage")
	@ResponseBody
	public List<OrmRole> listNoPage(PageRequest pageRequest){
		return ormRoleService.findAll(pageRequest.getFilterGroup());
	}
	
	@RequestMapping("/getRoleResourceTrees")
	@ResponseBody
	public List<ZtreeBean> getRoleResourceTrees(String roleId){
		return ormRoleService.getRoleResourceTrees(roleId);
	}
	
	@RequestMapping("/saveRoleResourceRight")
	@ResponseBody
	public void saveRoleResourceRight(String roleId,String resourceId){
		ormRoleService.saveRoleResourceRight(roleId,resourceId);
	}
	
}
