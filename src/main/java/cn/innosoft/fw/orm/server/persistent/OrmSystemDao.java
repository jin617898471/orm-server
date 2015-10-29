package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmSystem;

public interface OrmSystemDao extends BaseDao<OrmSystem, String> {
	
	public List<OrmSystem> findByValidSign(String validSign);
}
