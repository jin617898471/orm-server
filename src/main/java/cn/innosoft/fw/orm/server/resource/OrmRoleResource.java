package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.service.OrmRoleService;

@Controller
@RequestMapping(value = "system/role")
public class OrmRoleResource {
	
	@Autowired
	private OrmRoleService ormRoleService;
	

	@RequestMapping("/forward/manage")
	public String forwardManage(Model model){
		return "/orm/system/role/roleManage";
	}
	
	@RequestMapping("/forward/detail/{roleId}")
	public String forwardDetail(Model model,@PathVariable String roleId){
		OrmRole ormRole = ormRoleService.findOne(roleId);
		model.addAttribute("OrmRole",ormRole);
		model.addAttribute("sign","detail");
		return "/orm/system/role/roleADE";
	}
	
	@RequestMapping("/forward/add")
	public String forwardAdd(Model model){
		model.addAttribute("sign","add");
		return "/orm/system/role/roleADE";
	}
	
	@RequestMapping("/forward/edit/{roleId}")
	public String forwardEdit(Model model,@PathVariable String roleId){
		OrmRole ormRole = ormRoleService.findOne(roleId);
		model.addAttribute("ormRole",ormRole);
		model.addAttribute("sign","edit");
		return "/orm/system/role/roleADE";
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
	
}
