package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmCode;

public interface OrmCodeDao extends BaseDao<OrmCode, String> {
	
	public List<OrmCode> findByParentCodeId(String parentCodeId);

	public Long deleteBySystemId(String systemId);
	
	public List<OrmCode> findBySystemId(String systemId);
	
	public List<OrmCode> findBySystemIdAndParentCodeId(String systemId,String parentCodeId);
	
	@Query(value = "select * from ORM_CODE where SYSTEM_ID = ?1 and PARENT_CODE_ID = 'ROOT' ",nativeQuery=true)
	public List<OrmCode> getRootCodeBySystemId(String systemId);
	
}
