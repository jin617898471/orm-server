package cn.innosoft.fw.orm.server.webservice;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import cn.innosoft.fw.orm.server.service.OrmUserService;

@Service
@WebService(endpointInterface = "cn.innosoft.fw.orm.server.webservice.WsAuthentication", serviceName = "UserService", targetNamespace = "http://webservice.server.orm.fw.innosoft.cn/")
public class UserServiceImpl implements UserService {
	
	@Resource
	private OrmUserService ormUserService;
	

	@Override
	public String changePassword(String userId, String oldPwd, String newPwd) {
		String result = ormUserService.changePassword(userId, oldPwd, newPwd);
		return result;
	}
	
}
