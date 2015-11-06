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
import cn.innosoft.fw.biz.log.FwLog;
import cn.innosoft.fw.biz.log.FwLogFactory;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.service.OrmResourceService;
import cn.innosoft.fw.orm.server.service.OrmSystemService;

@Controller
@RequestMapping(value = "resource/ormresource")
public class OrmResourceResource {

	@Autowired
	private OrmResourceService ormResourceService;

	@Autowired
	private OrmSystemService ormSystemService;
	
	private FwLog log = FwLogFactory.getLog(this.getClass());

	/**
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/forward/manage")
	public String forwardManageAction() {
		return "orm/system/resource/ormResourceManage";
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
		return "orm/system/resource/ormResourceADE";
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
		log.info("新增资源");
		String str = ormResourceService.addResource(ormResource);
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
		return "orm/system/resource/ormResourceADE";
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
		log.info("修改资源");
		ormResourceService.updateResource(ormResource);
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
		return "orm/system/resource/ormResourceADE";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void deleteAction(@PathVariable String id) {
		log.info("删除资源");
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
		log.info("批量删除资源");
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
	public PageResponse<OrmResource> getListAction(String systemId,PageRequest pageRequest) {
		return ormResourceService.findValid(pageRequest);
	}

	/**
	 * 用于列表和下拉框显示：所属系统
	 * @return
	 */
	@RequestMapping("/systemList")
	@ResponseBody
	public List<OrmSystem> getSystemList(){
		return ormSystemService.findOrmSystemAll();
	}
	/**
	 * 加载资源树
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeBeanList")
	@ResponseBody
	public String getTreeBeanAction() throws Exception {
		return ormResourceService.getBcTreeNodes();
	}
}
