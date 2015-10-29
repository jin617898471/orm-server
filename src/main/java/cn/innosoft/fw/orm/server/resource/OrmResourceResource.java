package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.service.OrmResourceService;
import cn.innosoft.fw.orm.server.service.OrmSystemService;

@Controller
@RequestMapping(value = "resource")
public class OrmResourceResource {

	@Autowired
	private OrmResourceService ormResourceService;

	@Autowired
	private OrmSystemService ormSystemService;

	/**
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/forward/manage")
	public String forwardAddAction() {
		return "orm/resource/ormresource/ormResourceManage";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forward/add")
	public String forwardAddAction(Model model) {
		model.addAttribute("sign", "add");
		return "orm/resource/ormresource/ormResourceADE";
	}

	/**
	 * 添加资源
	 * 
	 * @param ormResource
	 * @return 如果资源类型与同级不匹配则返回N+系统Id+资源类型
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public String addAction(OrmResource ormResource) {
		String str = ormResourceService.addResource(ormResource);
		if (!"N".equals(str)) {
			str = str + "," + ormResource.getSystemId() + "," + ormResource.getResourceType();
		}
		/*
		 * String systemid = ormResource.getSystemId(); if
		 * ("GLOBAL".equals(systemid)) { //如果添加全局资源，更新登陆用户的资源 systemid =
		 * LoginUserContext.getCurrentLoginSystemId(); }
		 * LoginUserContext.updateUserResources(systemid);
		 */
		return str;
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
		model.addAttribute("OrmResource", ormResourceService.findByReosurceId(id));
		model.addAttribute("sign", "edit");
		return "orm/resource/ormresource/ormResourceADE";
	}

	/**
	 * 编辑资源
	 * 
	 * @param ormResource
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String updateAction(OrmResource ormResource) {
		ormResourceService.update(ormResource);
		return ormResource.getResourceId();
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
		model.addAttribute("OrmResource", ormResourceService.findByReosurceId(id));
		model.addAttribute("sign", "detail");
		return "orm/resource/ormresource/ormResourceADE";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void deleteAction(@PathVariable String id) {
		ormResourceService.delete(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 */
	@RequestMapping("/deletebatch/{idArray}")
	@ResponseBody
	public void deletebatchAction(@PathVariable ArrayList<String> idArray) {
		ormResourceService.deleteByIds(idArray);
	}

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
	public PageResponse<OrmResource> getListAction(PageRequest pageRequest) {
		return ormResourceService.findValid(pageRequest);
	}

	/**
	 * 加载资源树
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/treeBeanList")
//	@ResponseBody
//	public String getTreeBeanAction() throws Exception {
//		return ormResourceService.getBcTreeNodes();
//	}
}
