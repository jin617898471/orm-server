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
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.service.OrmSystemService;

@Controller
@RequestMapping(value = "authority/system")
public class OrmSystemResource {
	
	@Autowired
	private OrmSystemService ormSystemService;

	
	@RequestMapping("/forward/manage")
	public String forwardManage(Model model){
		return "/orm/authority/system/systemManage";
	}
	
	@RequestMapping("/forward/detail/{systemId}")
	public String forwardDetail(Model model,@PathVariable String systemId){
		OrmSystem ormSystem = ormSystemService.findOne(systemId);
		model.addAttribute("ormSystem",ormSystem);
		model.addAttribute("sign","detail");
		return "/orm/authority/system/systemADE";
	}
	
	@RequestMapping("/forward/add")
	public String forwardAdd(Model model){
		model.addAttribute("sign","add");
		return "/orm/authority/system/systemADE";
	}
	
	@RequestMapping("/forward/edit/{systemId}")
	public String forwardEdit(Model model,@PathVariable String systemId){
		OrmSystem ormSystem = ormSystemService.findOne(systemId);
		model.addAttribute("ormSystem",ormSystem);
		model.addAttribute("sign","edit");
		return "/orm/authority/system/systemADE";
	}

	
	@RequestMapping("/add")
	@ResponseBody
	public void addSystem(OrmSystem ormSystem){
		ormSystemService.addSystem(ormSystem);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public void editSystem(OrmSystem ormSystem,String[] updateField){
		ormSystemService.updateSystem(ormSystem,Arrays.asList(updateField));
	}
	
	@RequestMapping("/delete/{systemId}")
	@ResponseBody
	public void delete(@PathVariable String systemId){
		ormSystemService.deleteSystem(systemId);
	}
	
	@RequestMapping("/deletes/{idArray}")
	@ResponseBody
	public void deletes(@PathVariable ArrayList<String> idArray){
		ormSystemService.deleteSystem(idArray);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageResponse<OrmSystem> list(PageRequest pageRequest){
		return ormSystemService.findAll(pageRequest);
	}
	
	@RequestMapping(value="/js",produces="text/javascript")
	@ResponseBody
	public String js(){
		return ormSystemService.createJs();
	}
	
}
