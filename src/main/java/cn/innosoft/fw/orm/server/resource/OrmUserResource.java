package cn.innosoft.fw.orm.server.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.model.SelectTreeBean;
import cn.innosoft.fw.orm.server.service.OrmOrganizationService;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value = "user")
public class OrmUserResource {

	@Autowired
	private OrmUserService ormUserService;
	@Autowired
	private OrmOrganizationService ormOrganizationService;
	@RequestMapping("/manager")
	public String forwardUserManager() {
		return "orm/org/user/ormUserManage";
	}
	@RequestMapping("/list")
	@ResponseBody 
	public PageResponse<OrmUser> listUsers(PageRequest pageRequest){
		return ormUserService.find(pageRequest);
	}
	@RequestMapping("/detail/{userId}")
	public String forwardUserDetail(Model model, @PathVariable String userId){
		model.addAttribute("OrmUser", ormUserService.getUserById(userId));
		model.addAttribute("sign", "detail");
		return "orm/org/user/ormUserADE";
	}

	
	@RequestMapping("/selectTreeOrg")
	@ResponseBody
	public List<SelectTreeBean> getOrgSelectTree(){
		return ormOrganizationService.createSelectTree();
	}
}
