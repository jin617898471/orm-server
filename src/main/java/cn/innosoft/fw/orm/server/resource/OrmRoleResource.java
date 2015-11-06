package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.model.SelectTreeBean;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.service.OrmOrganizationService;
import cn.innosoft.fw.orm.server.service.OrmResourceService;
import cn.innosoft.fw.orm.server.service.OrmRoleService;
import cn.innosoft.fw.orm.server.service.OrmSystemService;

@Controller
@RequestMapping(value = "role/ormrole")
public class OrmRoleResource {
	
	@Autowired
	private OrmRoleService ormRoleService;
	
	@Autowired
	private OrmSystemService ormSystemService;
	
	@Autowired
	private OrmResourceService ormResourceService;
	
	private FwLog log = FwLogFactory.getLog(this.getClass());
	
	/**
	 * 跳转到角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/forward/manage")
	public String forwardManageAction() {
		return "orm/system/role/ormRoleManage";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/forward/add")
	public String forwardAddAction(Model model) {
		model.addAttribute("sign", "add");
		return "orm/system/role/ormRoleADE";
	}

	/**
	 * 添加角色
	 * 
	 * @param ormRole
	 * @return 
	 */
	@RequestMapping("/add")
	@ResponseBody
	public void addAction(OrmRole ormRole) {
		log.info("新增角色");
		ormRole.setRoleType("NORMAL");
		//ormRole.setCreateUserId(LoginUserContext.getUserId());
		ormRole.setCreateDt(new Date());
		//ormRole.setUpdateUserId(LoginUserContext.getUserId());
		ormRole.setUpdateDt(new Date());
		ormRoleService.addRole(ormRole);
	}

	/**
	 * 跳转到编辑界面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/forward/edit/{id}")
	public String forwardEditAction(Model model, @PathVariable String id) {
		model.addAttribute("OrmRole", ormRoleService.findByRoleId(id));
		model.addAttribute("sign", "edit");
		return "orm/system/role/ormRoleADE";
	}

	/**
	 * 跳转到权限建模界面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/forward/grant/{id}")
	public String forwardGrantAction(Model model, @PathVariable String id) {
		OrmRole ormRole = ormRoleService.findByRoleId(id);
		model.addAttribute("OrmRole", ormRole);
		model.addAttribute("sign", "grant");
		return "orm/system/role/ormRoleGrant";
	}
	
	/**
	 * 权限建模查询整个资源树
	 * 
	 * @return
	 */
	@RequestMapping("/creatResourceTreeBean")
	@ResponseBody
	public List<ZtreeBean> creatResourceTreeBeanAction(String roleId, String systemId) {
		return ormResourceService.creatResourceTreeBean(roleId, systemId);
	}
	
	/**
	 * 编辑角色
	 * 
	 * @param ormRole
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String updateAction(OrmRole ormRole) {
		log.info("修改角色");
		ormRoleService.updateRole(ormRole);
		return ormRole.getRoleId();
	}

	/**
	 * 跳转到查看界面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/forward/detail/{id}")
	public String forwardDetailAction(Model model, @PathVariable String id) {
		model.addAttribute("OrmRole", ormRoleService.findByRoleId(id));
		model.addAttribute("sign", "detail");
		return "orm/system/role/ormRoleADE";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void deleteAction(@PathVariable String id) {
		log.info("删除角色");
		ormRoleService.deleteRole(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 */
	@RequestMapping("/deletebatch/{idArray}")
	@ResponseBody
	public void deletebatchAction(@PathVariable ArrayList<String> idArray) {
		log.info("批量删除角色");
		ormRoleService.deleteByIds(idArray);
	}

	/**
	 * 组织机构选择下拉框
	 * 
	 * @return
	 */
//	@RequestMapping("/getOrgnizationGroup")
//	@ResponseBody
//	public List<SelectTreeBean> getOrgnizationGroup() {
//		return ormOrganizationService.createSelectTree();
//	}

	/**
	 * 系统选择下拉框
	 * 
	 * @return
	 */
	@RequestMapping("/getSystemGroup")
	@ResponseBody
	public List<OrmSystem> getSystemGroup() {
		return ormSystemService.findOrmSystemAll();
	}

	/**
	 * 分页查询列表
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResponse<OrmRole> getListAction(String systemId,PageRequest pageRequest) {
		return ormRoleService.findValid(pageRequest);
	}
	
	/**
	 * 查询system第一条记录的id和系统关联的角色ids
	 * 
	 * @return
	 */
	@RequestMapping("/system/roleids")
	@ResponseBody
	public List<String> getRoleIdsBySystemIdAction() {
		List<OrmSystem> orms = ormSystemService.findOrmSystemAll();
		List<String> map = new ArrayList<String>();
		if (orms != null && orms.size() > 0) {
			map.add(orms.get(0).getSystemId());
			//LoginUserContext.getUserAllSystemRoles()
			//OrmUser user = new OrmUserService().findByUserId(LoginUserContext.getUserId());
			//map.add(Util.convertListToString(ormRoleService.findRoleBySystemId(user.get), false));
			map.add(OrmResourceService.convertListToString(ormRoleService.findValidRoleIdList(), false));
		}
		return map;
	}
		
	/**
	 * 资源授权
	 */
	@RequestMapping("/grantResource")
	@ResponseBody
	public Map<String, Object> grantResource(String roleId, String resourceIds) throws Exception {
		log.info("修改角色的资源权限");
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			ormRoleService.grantResource(roleId, resourceIds);
			map.put("isSuccess", "true");
		} catch (Exception e) {
			map.put("isSuccess", "false");
			e.printStackTrace();
		}
		return map;
	}
}
