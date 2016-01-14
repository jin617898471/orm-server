package cn.innosoft.fw.orm.server.persistent;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmCode;

public interface OrmCodeDao extends BaseDao<OrmCode, String> {

	@Query(value = "SELECT * FROM ORM_CODE WHERE SYSTEM_ID IN( ?1 ) CONNECT BY PRIOR CODE_ID = PARENT_CODE_ID START WITH PARENT_CODE_ID = 'ROOT' ORDER BY ORDER_NUMBER ASC", nativeQuery = true)
	public List<OrmCode> findAllBySystemId(List<String> rightsytem);

	@Query(value = "SELECT * FROM ORM_CODE WHERE SYSTEM_ID IN( ?1 ) CONNECT BY PRIOR CODE_ID = PARENT_CODE_ID START WITH PARENT_CODE_ID = 'ROOT' AND IS_RIGHT = 'Y' ORDER BY ORDER_NUMBER ASC ", nativeQuery = true)
	public List<OrmCode> findHasRightBySystemId(List<String> rightsytem);

	public void deleteBySystemId(String systemId);

	public Integer countByRootCodeIdAndCodeValueAndCodeIdNot(String rootCodeId, String codeValue, String codeId);

	public Integer countByParentCodeIdAndCodeValueAndCodeIdNot(String string, String codeValue, String codeId);

	@Query(value = "SELECT * FROM ORM_CODE CONNECT BY PRIOR CODE_ID=  PARENT_CODE_ID START WITH CODE_ID IN (?1)", nativeQuery = true)
	public List<OrmCode> getAllChildrenNode(List<String> list);

	@Modifying
	@Query(value = "UPDATE ORM_CODE SET IS_RIGHT = ?1 WHERE CODE_ID IN (SELECT CODE_ID FROM ORM_CODE  CONNECT BY PRIOR CODE_ID=  PARENT_CODE_ID START WITH CODE_ID IN (?2) )", nativeQuery = true)
	public void updateIsRight(String isRight, String codeId);

	@Modifying
	@Query(value = "update ORM_CODE set order_number=order_number+1 where ORDER_NUMBER >?1 and ORDER_NUMBER<?2 and PARENT_CODE_ID=?3 ", nativeQuery = true)
	public void addOrderNumber(BigDecimal start,BigDecimal end, String parentCodeId);

	@Modifying
	@Query(value = "update ORM_CODE set order_number=order_number-1 where ORDER_NUMBER >?1 and ORDER_NUMBER<?2 and PARENT_CODE_ID=?3 ", nativeQuery = true)
	public void substractOrderNumber(BigDecimal start,BigDecimal end, String parentCodeId);

	@Modifying
	@Query(value = "update ORM_CODE set order_number=?1 where CODE_ID=?2", nativeQuery = true)
	public void updateOrderNumber(BigDecimal order, String sourceId);

	@Query(value = "select new cn.innosoft.fw.orm.server.model.OrmCode(max(orderNumber)) from OrmCode where parentCodeId=?1", nativeQuery = false)
	public OrmCode getMaxOrderNumber(String parentCodeId);

}
