package cn.innosoft.fw.orm.server.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "user")
public class OrmUserResource {

	@RequestMapping("/manager")
	public String forwardUserManager() {
		return "orm/ormuser/ormUserManage";
	}
}
