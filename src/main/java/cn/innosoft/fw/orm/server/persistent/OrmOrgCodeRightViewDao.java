package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrgCodeRightView;

public interface OrmOrgCodeRightViewDao extends BaseDao<OrmOrgCodeRightView, String> {
	List<OrmOrgCodeRightView> findByOrgId(String orgId);
}
