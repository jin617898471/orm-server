package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmCode;

public interface OrmCodeDao extends BaseDao<OrmCode, String> {
	
	@Query(value = "SELECT * FROM ORM_CODE WHERE SYSTEM_ID IN( ?1 ) CONNECT BY PRIOR CODE_ID = PARENT_CODE_ID START WITH PARENT_CODE_ID = 'ROOT'",nativeQuery=true)
	public List<OrmCode> findAllBySystemId(List<String> rightsytem);
	
	@Query(value = "SELECT * FROM ORM_CODE WHERE SYSTEM_ID IN( ?1 ) CONNECT BY PRIOR CODE_ID = PARENT_CODE_ID START WITH PARENT_CODE_ID = 'ROOT' AND IS_RIGHT = 'Y' ",nativeQuery=true)
	public List<OrmCode> findHasRightBySystemId(List<String> rightsytem);

	public void deleteBySystemId(String systemId);

	public Integer countByRootCodeIdAndCodeValueAndCodeIdNot(String rootCodeId, String codeValue, String codeId);

	public Integer countByParentCodeIdAndCodeValueAndCodeIdNot(String string, String codeValue, String codeId);

	@Query(value = "SELECT * FROM ORM_CODE CONNECT BY PRIOR CODE_ID=  PARENT_CODE_ID START WITH CODE_ID IN (?1)",nativeQuery=true)
	public List<OrmCode> getAllChildrenNode(List<String> list);

}
