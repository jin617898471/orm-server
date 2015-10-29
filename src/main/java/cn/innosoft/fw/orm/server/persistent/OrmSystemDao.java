package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmSystem;

public interface OrmSystemDao extends BaseDao<OrmSystem, String> {
	
	public List<OrmSystem> findByValidSign(String validSign);
	
	@Query(value="select system_id from orm_system where valid_sign = ?1",nativeQuery=true)
	public List<String> findSystemIdByValidSign(String validSign);
}
