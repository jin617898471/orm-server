package cn.innosoft.fw.orm.server.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.service.OrmSystemService;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value = "ormsystem")
public class OrmSystemResource {
	private static final Logger logger = LogManager.getLogger(OrmSystemResource.class);
	@Autowired
	private OrmSystemService ormSystemService;
	
	/**
	 * 通过传入的SystemId来返回一个System对象
	 * 然后跳转到系统详情页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/forward/manage")
	public String forwardManager(){
		return "orm/system/system/ormSystemManage";
	}
	@RequestMapping("/forward/details/{id}")
	public String forwardDetailsAction(Model model,@PathVariable String id){
		
		OrmSystem ormSystem = ormSystemService.findOne(id);
		model.addAttribute("OrmSystem",ormSystem);
		model.addAttribute("sign","details");
		//返回到系统详情页面

		return "orm/system/system/ormSystemADE";
	}
	
	/**
	 * 通过传入的SystemId来返回一个System对象
	 * 然后跳转到系统编辑页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/forward/edit/{id}")
	public String forwardEditAction(Model model,@PathVariable String id){
		
		OrmSystem ormSystem = ormSystemService.findOne(id);
		model.addAttribute("OrmSystem",ormSystem);
		model.addAttribute("sign","edit");
		//返回到系统详情页面
		return "orm/system/system/ormSystemADE";
	}
	
	/**
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/forward/add")
	public String forwardAddAction(Model model){
		
		model.addAttribute("sign","add");
		//返回到系统详情页面
		return "orm/system/system/ormSystemADE";
	}
	
	/**
	 * 分页查询
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/findByPage")
	@ResponseBody
	public PageResponse<OrmSystem>  findOrmSystemByPage(PageRequest pageRequest){
		return ormSystemService.find(pageRequest);
	}
	
	/**
	 * 用于添加和修改OrmSystem时系统标识符的唯一判断
	 * @param systemId
	 * @return
	 */
	@RequestMapping("/checkOnlyOne/{systemCode}")
	@ResponseBody
	public boolean checkOnlyOne(@PathVariable String systemCode){
		return ormSystemService.checkSystemOnly(systemCode);
	}
	
	/**
	 * 通过前端传过来的OrmSystem对象来添加数据
	 * @param ormSystem
	 * @return
	 */
	@RequestMapping("/addSystem")
	@ResponseBody
	public String addSystemAction(OrmSystem ormSystem){
		try {
			ormSystem.setCreateDt(new Date());
			ormSystemService.add(ormSystem);
			return "true";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "false";
		}
		//没有出现异常,返回"true"到前端
	}
	
	/**
	 * 通过前端传过来的OrmSystem对象来修改数据
	 * @param ormSystem
	 * @return
	 */
	@RequestMapping("/editSystem")
	@ResponseBody
	public String editSystemAction(OrmSystem ormSystem){
		ormSystem.setUpdateDt(new Date());
		String[] colums = new String[]{"systemDesc","systemName","updateDt","updateUserId"};
		ormSystemService.updateSome(ormSystem,Arrays.asList(colums));
		//没有出现异常,返回"true"到前端
		return "true";
	}
	
	/**
	 * 通过ID来删除系统数据
	 * @param id
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void deleteSystemAction(@PathVariable String id){
		ormSystemService.deleteSystem(id);
	}
	
	/**
	 * 根据以逗号分隔的OrmSystemId字符串批量删除数据
	 * @param idArray
	 */
	@RequestMapping("/deletebatch/{idArray}")
	@ResponseBody
	public void deleteBatchSystemAction(@PathVariable ArrayList<String> idArray){
		ormSystemService.deleteBatchSystemById(idArray);
	}
	
	/**
	 * 查询所有的OrmSystem信息
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<OrmSystem> findOrmSystemAll(){
		return ormSystemService.findOrmSystemAll();
	}
}
