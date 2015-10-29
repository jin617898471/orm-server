package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.orm.server.model.OrmCode;
import cn.innosoft.fw.orm.server.service.OrmCodeService;

@Controller
@RequestMapping(value = "ormCode")
public class OrmCodeResource {
	
	@Autowired
	private OrmCodeService ormCodeService;
	
	
	
	public OrmCodeService getOrmCodeService() {
		return ormCodeService;
	}

	public void setOrmCodeService(OrmCodeService ormCodeService) {
		this.ormCodeService = ormCodeService;
	}

	/**
	 * 跳转到详情界面
	 * @param model
	 * @param codeId
	 * @return
	 */
	@RequestMapping("/forward/details/{codeId}")
	public String forwardDetailAction(Model model,@PathVariable String codeId){
		OrmCode ormCode = ormCodeService.findOne(codeId);
		model.addAttribute("OrmCode",ormCode);
		return "";
	}
	
	/**
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/forward/add")
	public String forwardAddAction(Model model){
		model.addAttribute("type","add");
		return "";
	}
	
	/**
	 * 跳转到编辑页面
	 * @param model
	 * @param codeId
	 * @return
	 */
	@RequestMapping("/forward/edit/{codeId}")
	public String forwardEditAction(Model model,@PathVariable String codeId){
		OrmCode ormCode = ormCodeService.findOne(codeId);
		model.addAttribute("ormCode",ormCode);
		model.addAttribute("type","add");
		return "";
	}
	
	/**
	 * 添加OrmCode数据
	 * @param ormCode
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addOrmCodeAction(OrmCode ormCode){
		
		ormCodeService.add(ormCode);
		return "true";
	}
	
	/**
	 * 修改OrmCode数据
	 * @param ormCode
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String editOrmCodeAction(OrmCode ormCode){
		ormCodeService.update(ormCode);
		return "true";
	}
	
	/**
	 * 通过ormCodeId删除OrmCode数据
	 * @param ormCodeId
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void deleteOrmCodeAction(@PathVariable String ormCodeId){
		ormCodeService.delete(ormCodeId);
	}
	
	/**
	 * 批量删除OrmCode数据
	 * @param idArray
	 */
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public void deleteBatchCodeAction(@PathVariable ArrayList<String> idArray){
		ormCodeService.deleteBatchCode(idArray);
	}
	
	@RequestMapping("/findByPage")
	@ResponseBody
	public PageResponse<OrmCode> findAllOrmCode(PageRequest pageRequest){
		return ormCodeService.find(pageRequest);
	}
	
}
