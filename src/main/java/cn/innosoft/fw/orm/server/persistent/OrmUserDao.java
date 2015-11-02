package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRole;
import cn.innosoft.fw.orm.server.model.OrmUser;

public interface OrmUserDao extends BaseDao<OrmUser, String> {
	public List<OrmUser> findByUserAcct(String userAcct);
	Long deleteByUserIdIn(List<String> userIds);

	public OrmUser findByUserId(String userId);
}
