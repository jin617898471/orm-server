package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmResourceDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;

@Service
public class OrmResourceService extends AbstractBaseService<OrmResource, String> {

	@Autowired
	private OrmResourceDao ormResourceDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;

	@Override
	public BaseDao<OrmResource, String> getBaseDao() {
		return null;
	}

	/**
	 * 资源查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmResource> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmResource> page = findAll(group, pageRequest);
		return page;
	}

	/**
	 * 添加资源
	 * 
	 * @param ormResource
	 */
	public void addResource(OrmResource ormResource) {
		ormResourceDao.save(ormResource);
	}

	/**
	 * 更新资源
	 * 
	 * @param ormResource
	 */
	public void updateResource(OrmResource ormResource) {
		ormResourceDao.update(ormResource);
	}

	/**
	 * 删除资源
	 * 
	 * @param resId
	 */
	public void deleteResource(String resId) {
		ormResourceDao.delete(resId);
		ormRoleResourceRightDao.deleteByResourceId(resId);
	}

	public void editRoleResourceRight(List<String> roleIds, OrmResource resource) {
		String resId = resource.getResourceId();
		String parentResId = resource.getParentResId();
		String systemId = resource.getSystemId();
		for (String roleId : roleIds) {
			List<OrmRoleResourceRight> list = ormRoleResourceRightDao.findByRoleIdAndResourceId(roleId, parentResId);
			if (list.size() == 0) {
				createOrmRoleResourceRight(roleId, parentResId, "Y", systemId);
			}
			createOrmRoleResourceRight(roleId, resId, "N", systemId);
		}
	}
	
	/**
	 * 生成ZTREE，权限建模的时候用
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ZtreeBean> createZtree(String roleId) {
		List<ZtreeBean> result = new ArrayList<ZtreeBean>();
		List<OrmResource> resources = ormResourceDao.findAll();
		List<OrmRoleResourceRight> rights = ormRoleResourceRightDao.findByRoleId(roleId);
		for (OrmResource res : resources) {
			ZtreeBean node = resToTreeNode(res);
			String resId = res.getResourceId();
			for (OrmRoleResourceRight rrr : rights) {
				if (resId.equals(rrr.getResourceId()) && "N".equals(rrr.getHalfSelect())) {
					node.setChecked(true);
				}
			}
			result.add(node);
		}
		return result;
	}

	/**
	 * 生成资源ztree，建模是用
	 * 
	 * @param res
	 * @return
	 */
	private ZtreeBean resToTreeNode(OrmResource res) {
		ZtreeBean node = new ZtreeBean();
		node.setId(res.getResourceId());
		node.setpId(res.getParentResId());
		node.setName(res.getResourceName());
		boolean isParent = res.getIsLeaf() == "Y" ? false : true;
		node.setOpen(isParent);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("resType", res.getResourceType());
		node.setAttributes(attributes);
		node.setNocheck(false);
		node.setChecked(false);
		return node;
	}

	/**
	 * 创建资源角色关联
	 * 
	 * @param roleId
	 * @param resId
	 * @param halfSelcet
	 * @param autoAssociative
	 * @param systemId
	 * @return
	 */
	public void createOrmRoleResourceRight(String roleId, String resId, String halfSelcet, String systemId) {
		OrmRoleResourceRight rrr = new OrmRoleResourceRight();
		rrr.setHalfSelect(halfSelcet);
		rrr.setResourceId(resId);
		rrr.setRoleId(roleId);
		rrr.setSystemId(systemId);
		ormRoleResourceRightDao.save(rrr);
	}
}
