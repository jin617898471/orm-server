package cn.innosoft.fw.orm.server.webservice;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.log.FwLog;
import cn.innosoft.fw.biz.log.FwLogFactory;
import cn.innosoft.fw.biz.log.LogHelper;
import cn.innosoft.fw.biz.log.LogInterceptor;
import cn.innosoft.fw.biz.log.model.LogBusiness;
import cn.innosoft.fw.orm.server.service.OrmUserService;

@Service
@WebService(endpointInterface = "cn.innosoft.fw.orm.server.webservice.UserService", serviceName = "UserService", targetNamespace = "http://webservice.server.orm.fw.innosoft.cn/")
public class UserServiceImpl implements UserService {
	
	@Resource
	private OrmUserService ormUserService;
	
	private FwLog log = FwLogFactory.getLog(this.getClass());
	

	@Override
	public String changePassword(String userId, String oldPwd, String newPwd) {
		LogHelper.clean();
		LogBusiness operater = new LogBusiness();
		operater.setOperateUserId(userId);
		operater.setOperateDt( LogInterceptor.getTimestamp()  );
		LogHelper.addOperater(operater);
		try {
			log.info("通过webservice方式修改自己的密码");
			String result = ormUserService.changePassword(userId, oldPwd, newPwd);
			return result;
		} catch (Exception e) {
			
		}finally{
			LogHelper.reflash();
		}
		return "false";
	}
	
}
