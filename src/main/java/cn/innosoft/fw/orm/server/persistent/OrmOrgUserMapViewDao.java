package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMapView;

public interface OrmOrgUserMapViewDao extends BaseDao<OrmOrgUserMapView, String> {
	List<OrmOrgUserMapView> findByOrgId(String orgId);
}
