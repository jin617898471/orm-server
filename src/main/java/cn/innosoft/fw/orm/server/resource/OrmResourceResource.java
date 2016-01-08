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
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.service.OrmResourceService;

@Controller
@RequestMapping(value = "system/resource")
public class OrmResourceResource {

	@Autowired
	private OrmResourceService ormResourceService;

	
	@RequestMapping("/forward/manage")
	public String forwardManage(Model model){
		return "/orm/system/resource/resourceManage";
	}
	
	@RequestMapping("/forward/detail/{resourceId}")
	public String forwardDetail(Model model,@PathVariable String resourceId){
		OrmResource ormResource = ormResourceService.findOne(resourceId);
		model.addAttribute("OrmResource",ormResource);
		model.addAttribute("sign","detail");
		return "/orm/system/resource/resourceADE";
	}
	
	@RequestMapping("/forward/add")
	public String forwardAdd(Model model,String parentId,String parentType){
		OrmResource ormResource = ormResourceService.findParentNode(parentId,parentType);
		model.addAttribute("OrmResource",ormResource);
		model.addAttribute("sign","add");
		return "/orm/system/resource/resourceADE";
	}
	
	@RequestMapping("/forward/edit/{resourceId}")
	public String forwardEdit(Model model,@PathVariable String resourceId){
		OrmResource ormResource = ormResourceService.findOne(resourceId);
		model.addAttribute("ormResource",ormResource);
		model.addAttribute("sign","edit");
		return "/orm/system/resource/resourceADE";
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<ZtreeBean> getResourceTree(){
		return ormResourceService.findAllTrees();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public void addResource(OrmResource ormResource){
		ormResourceService.addResource(ormResource);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public void editResource(OrmResource ormResource,String[] updateField){
		ormResourceService.updateResource(ormResource,Arrays.asList(updateField));
	}
	
	@RequestMapping("/delete/{resourceId}")
	@ResponseBody
	public void delete(@PathVariable String resourceId){
		ormResourceService.deleteResource(resourceId);
	}
	
	@RequestMapping("/deletes/{idArray}")
	@ResponseBody
	public void deletes(@PathVariable ArrayList<String> idArray){
		ormResourceService.deleteResource(idArray);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageResponse<OrmResource> list(PageRequest pageRequest){
		return ormResourceService.findAll(pageRequest);
	}

}
