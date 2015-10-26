package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmResource;

public interface OrmResourceDao extends BaseDao<OrmResource, String> {
	public List<OrmResource> findByParentResId(String parentResId);

	public Long deleteBySystemId(String systemId);
}
