package cn.innosoft.fw.orm.server.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService
@SOAPBinding(style = Style.RPC)
public interface UserService {

	public String changePassword(String userId,String oldPwd, String newPwd);
	
}
