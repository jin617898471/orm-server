package cn.innosoft.fw.orm.server.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.orm.server.model.OrmUser;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Controller
@RequestMapping(value = "user")
public class OrmUserResource {

	@Autowired
	private OrmUserService ormUserService;
	@RequestMapping("/manager")
	public String forwardUserManager() {
		return "orm/ormuser/ormUserManage";
	}
	@RequestMapping("/list")
	@ResponseBody
	public PageResponse<OrmUser> listUsers(PageRequest pageRequest){
		return ormUserService.find(pageRequest);
	}
	@RequestMapping("/detail")
	public String forwardUserDetail(){
		return "orm/ormuser/ormUserManage";
	}
}
