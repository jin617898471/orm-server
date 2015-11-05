package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmResourceDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmSystemDao;
import cn.innosoft.fw.orm.server.util.StringUtil;

@Service
public class OrmResourceService extends AbstractBaseService<OrmResource, String> {

	@Autowired
	private OrmResourceDao ormResourceDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmSystemDao ormSystemDao;

	/**
	 * 重载方法
	 */
	@Override
	public BaseDao<OrmResource, String> getBaseDao() {
		return ormResourceDao;
	}

	/**
	 * 添加资源
	 * 
	 * @param ormResource
	 */
	public String addResource(OrmResource ormResource) {
		String id = StringUtil.getUUID();
		ormResource.setResourceId(id);
		String parentId = ormResource.getParentResId();
		String resType = ormResource.getResourceType();
		String msg = compareParentResType(parentId, resType);
		if ("N".equals(msg)) {
			return msg;
		}
		ormResource.setValidSign("Y");
		ormResource.setIsLeaf("Y");
		ormResource.setCreateDt(new Date());
		// ormResource.setCreateUserId(LoginUserContext.getUserId());
		// ormResource.setCreateUserId(LoginUserContext.getUserId());
		ormResource.setUpdateDt(new Date());
		// ormResource.setUpdateUserId(LoginUserContext.getUserId());
		// ormResource.setUpdateUserId(LoginUserContext.getUserId());
		updateIfParentIsLeaf(parentId);
		OrmResource parentRes = ormResourceDao.findByResourceId(parentId);
		ormResource.setRootResId(parentRes.getRootResId());
		String str = ormResourceDao.save(ormResource).getResourceId() + "," + ormResource.getSystemId() + ","
				+ ormResource.getResourceType();
		return str;
	}

	/**
	 * 如果父亲节点是叶子节点，修改为N
	 * 
	 * @param parentId
	 */
	private void updateIfParentIsLeaf(String parentId) {
		OrmResource res = ormResourceDao.findByResourceId(parentId);
		if (res != null && "Y".equals((res.getIsLeaf()))) {
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
	
	public void deleteBySystemId(String systemId){
		ormResourceDao.deleteBySystemId(systemId);
	}
	/**
	 * 更新资源
	 * 
	 * @param ormResource
	 */
	public void updateResource(OrmResource ormResource) {
		//ormResource.setUpdateUserId(LoginUserContext.getUserId());
		ormResource.setUpdateDt(new Date());
		ormResourceDao.update(ormResource);
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
	
	public void createSystemRes(OrmSystem system){
		String id = StringUtil.getUUID();
		OrmResource res = new OrmResource();
		res.setResourceId(id);
		res.setCreateDt(new Date());
		res.setIsLeaf("Y");
		res.setParentResId("ROOT");
		res.setValidSign("Y");
		res.setRootResId(id);
		res.setResourceCode(system.getSystemCode());
		res.setResourceName(system.getSystemName());
		res.setResourceType("000");
		res.setSystemId(system.getSystemId());
		ormResourceDao.add(res);
	}
	/**
	 * 生成资源ztree<建模>
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

	/**
	 * 加载资源树
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getBcTreeNodes() throws Exception {
		StringBuilder sb = new StringBuilder();
		List<String> systemIdList = ormSystemDao.findSystemIdByValidSign("Y");
		String systemid = convertListToString(systemIdList, false);
		FilterGroup filtergroup = QueryConditionHelper.add(new String[] { "validSign", "systemId", "resourceType" },
				new String[] { "Y", systemid, "000" }, new String[] { "equal", "in", "equal" });
		List<OrmResource> topist = findAll(filtergroup);
		FilterGroup rfiltergroup = QueryConditionHelper.add(new String[] { "validSign" }, new String[] { "Y" });
		List<OrmResource> rlist = findAll(rfiltergroup);
		List<String> resourceIds = new ArrayList<String>();
		for (String systemId : systemIdList) {
			resourceIds.addAll(ormResourceDao.findResourceIdBySystemId(systemId));
		}
		sb.append("[");
		sb.append(getResourceString(topist, rlist, resourceIds));
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 将字符串转换成List
	 * 
	 * @param ids
	 * @param hasQuotation
	 * @return
	 */
	public static String convertListToString(List<String> ids, boolean hasQuotation) {
		String str = "";
		for (String id : ids) {
			str += ",";
			if (hasQuotation) {
				str += "'" + id + "'";
			} else {
				str += id;
			}
		}
		if (str.length() > 0) {
			str = str.substring(1);
		}
		return str;
	}

	/**
	 * 将资源链表拼接成字符串
	 * 
	 * @param topResources
	 * @param systemResources
	 * @param resourceIds
	 * @return
	 */
	private static String getResourceString(List<OrmResource> topResources, List<OrmResource> systemResources,
			List<String> resourceIds) {
		StringBuilder sb = new StringBuilder();
		for (OrmResource resource : topResources) {
			StringBuilder sb2 = new StringBuilder();
			boolean checked = true;
			sb2.append("{");
			sb2.append("\"id\":").append("\"").append(resource.getResourceId()).append("\",");
			sb2.append("\"name\":").append("\"").append(resource.getResourceName()).append("\",");
			if ("000".equals(resource.getResourceType())) {
				sb2.append("\"type\":").append("\"").append("system").append("\",");
				sb2.append("\"open\":").append(true).append(",");
			} else {
				sb2.append("\"type\":").append("\"").append("resource").append("\",");
			}
			sb2.append("\"system\":").append("\"").append(resource.getSystemId()).append("\",");
			sb2.append("\"resourceType\":").append("\"").append(resource.getResourceType()).append("\"");

			if (!resourceIds.contains(resource.getResourceId())) {
				sb2.append(",\"nocheck\":").append("true");
				checked = false;
			}

			List<OrmResource> childResources = getSameLevelResources(resource.getResourceId(), systemResources);
			boolean hasChild = false;
			if (childResources != null && childResources.size() > 0) {
				String temp = getResourceString(childResources, systemResources, resourceIds);
				if (StringUtils.hasText(temp)) {
					hasChild = true;
					sb2.append(",\"children\":").append("[");
					sb2.append(temp);
					sb2.append("]");
				}
			}
			sb2.append("},");
			if (checked) {
				sb.append(sb2);
			} else if (!checked && hasChild) {
				sb.append(sb2);
			}

		}
		if (sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 获得统计资源
	 * 
	 * @param pResourceId
	 * @param allResources
	 * @return
	 */
	private static List<OrmResource> getSameLevelResources(String pResourceId, List<OrmResource> allResources) {
		List<OrmResource> retResources = new ArrayList<OrmResource>();
		for (OrmResource resource : allResources) {
			if (pResourceId.equals(resource.getParentResId())) {
				retResources.add(resource);
				continue;
			}
		}
		return retResources;
	}

	/**
	 * 提供给权限建模资源树
	 * 
	 * @param roleId
	 * @param systemId
	 * @return
	 */
	public List<ZtreeBean> creatResourceTreeBean(String roleId, String systemId) {
		List<ZtreeBean> treeBeanList = new ArrayList<ZtreeBean>();
		// 已授权资源
		List<OrmRoleResourceRight> grantedResourceList = ormRoleResourceRightDao.findByRoleId(roleId);
		List<String> grantedResourceIdsList = new ArrayList<String>();

		for (OrmRoleResourceRight ormRoleResourceRight : grantedResourceList) {
			if ("N".equals(ormRoleResourceRight.getHalfSelect()))
				grantedResourceIdsList.add(ormRoleResourceRight.getResourceId());
		}
		// 有权限分配的资源id列表
		// List<String> enableGrantResourceIds =
		// LoginUserContext.getUserResources(systemId);
		List<String> enableGrantResourceIds = ormResourceDao.findResourceIdBySystemId(systemId);
		List<OrmResource> list = ormResourceDao.creatResourceTreeBean(systemId);
		list = removeUselessNode(list, enableGrantResourceIds);

		for (OrmResource resource : list) {
			ZtreeBean tree = createZtreeBean(resource, grantedResourceIdsList, enableGrantResourceIds);
			treeBeanList.add(tree);
		}
		return treeBeanList;
	}

	/**
	 * 移除不用的节点.
	 * 
	 * @param list
	 *            完整的结果集
	 * @param ids
	 *            有效的结果集
	 * @return
	 */
	private List<OrmResource> removeUselessNode(List<OrmResource> list, List<String> ids) {
		Map<String, OrmResource> map = conventListToMap(list);
		List<String> useids = new ArrayList<String>();
		for (String id : ids) {
			useids = fillUsedNode(map, useids, id);
		}
		List<OrmResource> usednode = new ArrayList<OrmResource>();
		for (OrmResource res : list) {
			if (isUsed(res, useids)) {
				usednode.add(res);
			}
		}
		return usednode;
	}

	/**
	 * 将list转换成Map
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, OrmResource> conventListToMap(List<OrmResource> list) {
		Map<String, OrmResource> map = new HashMap<String, OrmResource>();
		for (OrmResource res : list) {
			String resId = res.getResourceId();
			map.put(resId, res);
		}

		return map;
	}

	/**
	 * 填充节点
	 * 
	 * @param map
	 * @param useids
	 * @param id
	 * @return
	 */
	private List<String> fillUsedNode(Map<String, OrmResource> map, List<String> useids, String id) {
		OrmResource res = map.get(id);
		if (res != null) {
			if (!useids.contains(id)) {
				useids.add(id);
				String presId = res.getParentResId();
				useids = fillUsedNode(map, useids, presId);
			}
		}
		return useids;
	}

	/**
	 * 判断节点是否有用
	 * 
	 * @param res
	 * @param useids
	 * @return
	 */
	private boolean isUsed(OrmResource res, List<String> useids) {
		String resId = res.getResourceId();
		for (String id : useids) {
			if (id.equals(resId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 创建ZtreeBean资源树
	 * 
	 * @param resource
	 * @param grantedResourceIdsList
	 * @param enableGrantResourceIds
	 * @return
	 */
	private ZtreeBean createZtreeBean(OrmResource resource, List<String> grantedResourceIdsList,
			List<String> enableGrantResourceIds) {
		ZtreeBean treebean = new ZtreeBean();
		String id = resource.getResourceId();
		String name = resource.getResourceName();
		String pId = resource.getParentResId();
		Boolean isLeaf = ("Y" == resource.getIsLeaf()) ? true : false;
		treebean.setId(id);
		treebean.setName(name);
		treebean.setpId(pId);
		treebean.setOpen(!(isLeaf));
		treebean.setIsParent(isLeaf);
		treebean.setChecked(false);
		if (enableGrantResourceIds.contains(id) && !"000".equals(resource.getResourceType())) {
			treebean.setNocheck(false);
		} else {
			treebean.setNocheck(true);
		}

		// 是否选中
		if (grantedResourceIdsList.contains(id) || grantedResourceIdsList.contains(pId)) {
			treebean.setChecked(true);
		}
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("resourceType", resource.getResourceType());
		treebean.setAttributes(attributes);
		return treebean;
	}
}
