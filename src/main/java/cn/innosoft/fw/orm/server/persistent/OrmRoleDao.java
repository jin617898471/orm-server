package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmRole;

public interface OrmRoleDao extends BaseDao<OrmRole, String> {
	
	public void deleteBySystemId(String systemId);

	public List<OrmRole> findBySystemId(String systemId);
	
	@Query("select distinct r from OrmRole r, OrmUserRoleMap m where r.roleId=m.roleId and m.userId=?1")
	List<OrmRole> getRoleByUserid(String userId);
	
}
