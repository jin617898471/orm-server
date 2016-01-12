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
import cn.innosoft.fw.orm.server.model.OrmCode;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.service.OrmCodeService;

@Controller
@RequestMapping(value = "authority/code")
public class OrmCodeResource {
	
	@Autowired
	private OrmCodeService ormCodeService;

	
	@RequestMapping("/forward/manage")
	public String forwardManage(Model model){
		return "/orm/authority/code/codeManage";
	}
	
	@RequestMapping("/forward/detail/{codeId}")
	public String forwardDetail(Model model,@PathVariable String codeId){
		OrmCode ormCode = ormCodeService.findOne(codeId);
		model.addAttribute("ormCode",ormCode);
		model.addAttribute("sign","detail");
		return "/orm/authority/code/codeADE";
	}
	
	@RequestMapping("/forward/add")
	public String forwardAdd(Model model,String parentId,String parentType){
		OrmCode ormCode = ormCodeService.findParentNode(parentId,parentType);
		model.addAttribute("ormCode",ormCode);
		model.addAttribute("sign","add");
		model.addAttribute("parentType",parentType);
		return "/orm/authority/code/codeADE";
	}
	
	@RequestMapping("/forward/edit/{codeId}")
	public String forwardEdit(Model model,@PathVariable String codeId){
		OrmCode ormCode = ormCodeService.findOne(codeId);
		model.addAttribute("ormCode",ormCode);
		model.addAttribute("sign","edit");
		return "/orm/authority/code/codeADE";
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<ZtreeBean> getCodeTree(){
		return ormCodeService.findAllTrees();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public void addCode(OrmCode ormCode){
		ormCodeService.addCode(ormCode);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public void editCode(OrmCode ormCode,String[] updateField){
		ormCodeService.updateCode(ormCode,Arrays.asList(updateField));
	}
	
	@RequestMapping("/delete/{codeId}")
	@ResponseBody
	public void delete(@PathVariable String codeId){
		ormCodeService.deleteCode(codeId);
	}
	
	@RequestMapping("/deletes/{idArray}")
	@ResponseBody
	public void deletes(@PathVariable ArrayList<String> idArray){
		ormCodeService.deleteCode(idArray);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageResponse<OrmCode> list(PageRequest pageRequest){
		return ormCodeService.findAll(pageRequest);
	}
	
}
