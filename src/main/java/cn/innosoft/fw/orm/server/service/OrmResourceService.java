package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.Date;
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
import cn.innosoft.orm.client.service.LoginUserContext;

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
	public String addResource(OrmResource ormResource) {
		String parentId = ormResource.getParentResId();
		String resType = ormResource.getResourceType();
		String msg = compareParentResType(parentId, resType);
		if ("N".equals(msg)) {
			return msg;
		}
		ormResource.setValidSign("Y");
		ormResource.setIsLeaf("Y");
		ormResource.setCreateDt(new Date());
		ormResource.setCreateUserId(LoginUserContext.getUserId());
		ormResource.setUpdateDt(new Date());
		ormResource.setUpdateUserId(LoginUserContext.getUserId());
		updateIfParentIsLeaf(parentId);
		return ormResourceDao.save(ormResource).toString();
	}

	/**
	 * 如果父亲节点是叶子节点，修改为N
	 * 
	 * @param parentId
	 */
	private void updateIfParentIsLeaf(String parentId) {
		OrmResource res = ormResourceDao.findByResourceId(parentId);
		if ("Y".equals((res.getIsLeaf()))) {
			ormResourceDao.updateIsLeafByResourceId("N", res.getResourceId());
		}
	}

	/**
	 * 与父亲资源的资源类型进行对比，如果类型不匹配返回N，相同返回Y
	 * 
	 * @param parentId
	 *            父资源ID
	 * @param resType
	 *            子资源类型
	 * @return
	 */
	private String compareParentResType(String parentId, String resType) {
		List<OrmResource> parentList = ormResourceDao.findByParentResId(parentId);
		for (OrmResource parentRes : parentList) {
			if (!resType.equals(parentRes.getResourceType())) {
				return "N";
			}
		}
		return "Y";
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

	/**
	 * 通过Id查找资源
	 * 
	 * @param id
	 * @return
	 */
	public OrmResource findByReosurceId(String id) {
		return ormResourceDao.findByResourceId(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 */
	public void deleteByIds(ArrayList<String> idArray) {
		for (String string : idArray) {
			delete(string);
		}
	}

	/**
	 * 分页查询有效资源
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmResource> findValid(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmResource> page = findAll(group, pageRequest);
		return page;
	}

//	public String getBcTreeNodes() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		String systemid = Util.convertListToString(LoginUserContext.getUserId(), false);
//		FilterGroup filtergroup = QueryConditionHelper.add(new String[] { "validSign", "systemId", "resourceType" },
//				new String[] { "Y", systemid, "000" }, new String[] { "equal", "in", "equal" });
//		List<OrmResource> topist = this.find(filtergroup);
//		FilterGroup rfiltergroup = QueryConditionHelper.add(new String[] { "validSign" }, new String[] { "Y" });
//		List<OrmResource> rlist = this.find(rfiltergroup);
//		List<String> resourceIds = new ArrayList<String>();
//		for (String string : LoginUserContext.getSystem()) {
//			resourceIds.addAll(LoginUserContext.getUserResources(string));
//		}
//		sb.append("[");
//		sb.append(getResourceString(topist, rlist, resourceIds));
//		sb.append("]");
//		return sb.toString();
//	}

}
