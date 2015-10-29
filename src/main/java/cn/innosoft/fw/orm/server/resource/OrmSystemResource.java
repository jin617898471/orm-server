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
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.service.OrmSystemService;

@Controller
@RequestMapping(value = "ormsystem")
public class OrmSystemResource {

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
	@RequestMapping("/forward/details/{id}")
	public String forwardDetailsAction(Model model,@PathVariable String id){
		
		OrmSystem ormSystem = ormSystemService.findOne(id);
		model.addAttribute("OrmSystem",ormSystem);
		model.addAttribute("sign","details");
		//返回到系统详情页面
		return "/pages/ormsystem/ormSystemADE.jsp";
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
		return "pages/ormsystem/ormSystemADE.jsp";
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
		return "pages/ormsystem/ormSystemADE.jsp";
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
	 * 通过前端传过来的OrmSystem对象来添加数据
	 * @param ormSystem
	 * @return
	 */
	@RequestMapping("/addSystem")
	@ResponseBody
	public String addSystemAction(OrmSystem ormSystem){
		
		ormSystemService.add(ormSystem);
		//没有出现异常,返回"true"到前端
		return "true";
	}
	
	/**
	 * 通过前端传过来的OrmSystem对象来修改数据
	 * @param ormSystem
	 * @return
	 */
	@RequestMapping("/editSystem")
	@ResponseBody
	public String editSystemAction(OrmSystem ormSystem){
		
		ormSystemService.add(ormSystem);
		//没有出现异常,返回"true"到前端
		return "true";
	}
	
	/**
	 * 通过ID来删除系统数据
	 * @param id
	 */
	@RequestMapping("/deleteSystem/{id}")
	@ResponseBody
	public void deleteSystemAction(@PathVariable String id){
		
		ormSystemService.deleteSystem(id);
	}
	
	/**
	 * 根据以逗号分隔的OrmSystemId字符串批量删除数据
	 * @param idArray
	 */
	@RequestMapping("/deleteBatch/{idArray}")
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
	
	/**
	 * 根据Id查询OrmSystem信息
	 * @param ormSystemId
	 * @return
	 */
	public OrmSystem getOrmSystemById(String ormSystemId){
		return ormSystemService.findOne(ormSystemId);
	}
}
