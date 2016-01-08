package cn.innosoft.fw.orm.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.innosoft.fw.orm.server.service.OrmCodeService;
import cn.innosoft.fw.orm.server.service.OrmRoleService;
import cn.innosoft.fw.orm.server.service.OrmSystemService;

public class Test {
	public static void main(String[] args) {
		ApplicationContext applicationcontext = new ClassPathXmlApplicationContext(new String[]{"ormServer-beans.xml"});
		OrmCodeService dao = (OrmCodeService) applicationcontext.getBean("ormCodeService");
		System.out.println( dao.findHasRightTrees() );
		OrmRoleService dao2 = (OrmRoleService) applicationcontext.getBean("ormRoleService");
		System.out.println( dao2.findOrgResourceTrees("P_446") );
		System.out.println( dao2.findUserResourceTrees("39D948B395954E1786CBD36AB52A701B") );
		OrmSystemService dao3 = (OrmSystemService) applicationcontext.getBean("ormSystemService");
		System.out.println( dao3.createJs() );
	}
}
