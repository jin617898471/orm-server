package cn.innosoft.fw.orm.server.persistent;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmUser;

public interface OrmUserDao extends BaseDao<OrmUser, String> {
	
	public List<OrmUser> findByUserAcct(String userAcct);
	
	Long deleteByUserIdIn(List<String> userIds);
	
	List<OrmUser> findFirst10ByUserAcctLikeOrUserNameLikeOrderByCreateDt(String userAcct,String userName);
	
	@Query("select u from OrmUser u,OrmOrgUserMap m where u.userId=m.userId and m.orgId=?1")
	List<OrmUser> getUserByOrgId(String orgId);
}
 