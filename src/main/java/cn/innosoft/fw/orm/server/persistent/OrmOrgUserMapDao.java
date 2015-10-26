package cn.innosoft.fw.orm.server.persistent;

import java.util.Collection;
import java.util.List;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmOrgUserMap;

public interface OrmOrgUserMapDao extends BaseDao<OrmOrgUserMap, String> {
	public List<OrmOrgUserMap> findByUserId(String userId);

	public List<OrmOrgUserMap> findByOrgId(String orgId);

	public List<OrmOrgUserMap> findByOrgIdIn(Collection<String> orgIds);

	public List<OrmOrgUserMap> findByUserIdAndOrgId(String userId, String orgId);

	public Long deleteByUserIdAndOrgId(String userId, String orgId);

	public Long deleteByUserId(String userId);

	public Long deleteByOrgId(String orgId);

}
