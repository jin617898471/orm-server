package cn.innosoft.fw.orm.server.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.innosoft.fw.orm.client.model.OrmMenu;
import cn.innosoft.fw.orm.client.service.LoginUserContext;

@Controller
@RequestMapping(value = "orm/home")
public class HomeResource {
	
	@RequestMapping(value = "")
	public String forwardUserManager(HttpSession session,HttpServletResponse response) {
		String userName = LoginUserContext.getUserName();
		List<OrmMenu> resList = LoginUserContext.getMenu();
		session.setAttribute("userName", userName);
		session.setAttribute("resList", resList);
		String url = "orm/nav.jsp";
		if(resList!=null && resList.size()>0){
			
		}else{
			return "redirect:/noright.jsp";
		}
		return "redirect:/"+url;
	}
}
