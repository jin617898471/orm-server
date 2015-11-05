package cn.innosoft.fw.orm.server.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.innosoft.fw.orm.client.model.TreeNode;
import cn.innosoft.fw.orm.client.service.LoginUserContext;

@Controller
@RequestMapping(value = "home")
public class HomeResource {
	
	@RequestMapping(value = "/home")
	public String forwardUserManager(HttpSession session,HttpServletResponse response) {
		String userName = LoginUserContext.getUsername();
		List<TreeNode> resList = LoginUserContext.createResourceMenuTree();
		session.setAttribute("userName", userName);
		session.setAttribute("resList", resList);
		String url = "";
		if(resList.size()>0){
			url = resList.get(0).getValue();
		}
		return "redirect:/"+url;
//		try {
//			response.sendRedirect(url);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
}
