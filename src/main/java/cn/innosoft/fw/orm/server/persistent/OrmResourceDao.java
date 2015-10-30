package cn.innosoft.fw.orm.server.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.orm.server.model.OrmResource;

public interface OrmResourceDao extends BaseDao<OrmResource, String> {

	/**
	 * 查找有效同级节点（同一个父亲节点）
	 * 
	 * @param parentResId
	 * @return
	 */
	@Query(value = "select * from ORM_RESOURCE where parent_Res_Id=?1 and valid_Sign='Y'", nativeQuery = true)
	public List<OrmResource> findByParentResId(String parentResId);

	/**
	 * 通过SystemId删除资源
	 * 
	 * @param systemId
	 * @return
	 */
	public Long deleteBySystemId(String systemId);

	/**
	 * 通过资源Id查找资源
	 * 
	 * @param parentId
	 * @return
	 */
	public OrmResource findByResourceId(String resId);

	/**
	 * 修改是否为叶子节点
	 * @param isLeaf Y或N
	 * @param resourceId
	 */
	@Modifying
	@Query(value = "update ORM_RESOURCE set is_leaf = ?1 where resource_Id = ?2", nativeQuery = true)
	public void updateIsLeafByResourceId(String isLeaf, String resourceId);
	
	/**
	 * 通过系统Id查找资源Id
	 * @param systemId
	 * @return
	 */
	@Query(value="select resource_id from ORM_RESOURCE where system_id = ?1",nativeQuery=true)
	public List<String> findResourceIdBySystemId(String systemId);
}
