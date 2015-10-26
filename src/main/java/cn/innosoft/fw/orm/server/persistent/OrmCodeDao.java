package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmCode;

public interface OrmCodeDao extends BaseDao<OrmCode, String> {
	public List<OrmCode> findByParentCodeId(String parentCodeId);

	public Long deleteBySystemId(String systemId);
}
