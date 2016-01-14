package cn.innosoft.fw.orm.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.biz.utils.Identities;
import cn.innosoft.fw.orm.server.model.OrmResource;
import cn.innosoft.fw.orm.server.model.OrmRoleResourceRight;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.model.ZtreeBean;
import cn.innosoft.fw.orm.server.persistent.OrmResourceDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;

@Service
public class OrmResourceService extends AbstractBaseService<OrmResource, String> {

	@Autowired
	private OrmResourceDao ormResourceDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmSystemService ormSystemService;

	@Override
	public BaseDao<OrmResource, String> getBaseDao() {
		return ormResourceDao;
	}

	public List<ZtreeBean> findRoleResourceTrees(String roleId, String systemId) {
		List<OrmResource> resources = ormResourceDao.findAllBySystemId(Arrays.asList(new String[] { systemId }));
		List<ZtreeBean> codetrees = transferResourceTreeNode(resources);
		List<OrmRoleResourceRight> roleright = ormRoleResourceRightDao.findByRoleId(roleId);
		for (ZtreeBean bean : codetrees) {
			String id = bean.getId();
			for (OrmRoleResourceRight right : roleright) {
				if (right.getResourceId().equals(id)) {
					bean.setChecked(true);
				}
			}
		}
		return codetrees;
	}

	public List<ZtreeBean> findUserResourceTrees(String userId) {
		List<OrmSystem> ormSystems = ormSystemService.getAll();
		List<ZtreeBean> systemtree = transferSystemTreeNode(ormSystems);
		List<OrmResource> resources = ormResourceDao.findUserResource(userId);
		List<ZtreeBean> codetrees = transferResourceTreeNode(resources);
		systemtree.addAll(codetrees);
		return systemtree;
	}

	public List<ZtreeBean> findOrgResourceTrees(String orgId) {
		List<OrmSystem> ormSystems = ormSystemService.getAll();
		List<ZtreeBean> systemtree = transferSystemTreeNode(ormSystems);
		List<OrmResource> resources = ormResourceDao.findOrgResource(orgId);
		List<ZtreeBean> codetrees = transferResourceTreeNode(resources);
		systemtree.addAll(codetrees);
		return systemtree;
	}

	public List<ZtreeBean> findAllTrees() {
		List<OrmSystem> ormSystems = ormSystemService.getHasRight();
		List<ZtreeBean> systemtree = transferSystemTreeNode(ormSystems);
		List<String> rightsytem = getSystemId(systemtree);
		List<OrmResource> resources = ormResourceDao.findAllBySystemId(rightsytem);
		List<ZtreeBean> codetrees = transferResourceTreeNode(resources);
		systemtree.addAll(codetrees);
		return systemtree;
	}

	public List<ZtreeBean> findAllTreesBySystem(String systemId) {
		List<String> rightsytem = Arrays.asList(new String[] { systemId });
		List<OrmResource> resources = ormResourceDao.findAllBySystemId(rightsytem);
		List<ZtreeBean> codetrees = transferResourceTreeNode(resources);
		return codetrees;
	}

	private List<ZtreeBean> transferResourceTreeNode(List<OrmResource> resources) {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		for (OrmResource res : resources) {
			ZtreeBean node = createResourceTreeNode(res);
			list.add(node);
		}
		return list;
	}

	private ZtreeBean createResourceTreeNode(OrmResource res) {
		ZtreeBean node = new ZtreeBean();
		node.setId(res.getResourceId());
		String pId = res.getParentResId();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("nodeType", "RESOURCE");
		attributes.put("oldPid", pId);
		if("ROOT".equals(pId)){
			pId=res.getSystemId();
		}
		node.setpId(pId);
		node.setName(res.getResourceName());
		node.setAttributes(attributes);
		node.setIconSkin((String) attributes.get("nodeType"));
		return node;
	}

	private List<ZtreeBean> transferSystemTreeNode(List<OrmSystem> ormSystems) {
		List<ZtreeBean> list = new ArrayList<ZtreeBean>();
		for (OrmSystem sys : ormSystems) {
			ZtreeBean node = createSytemTreeNode(sys);
			list.add(node);
		}
		return list;
	}

	private ZtreeBean createSytemTreeNode(OrmSystem sys) {
		ZtreeBean node = new ZtreeBean();
		node.setId(sys.getSystemId());
		node.setpId("ROOT");
		node.setName(sys.getSystemName());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("nodeType", "SYSTEM");
		node.setAttributes(attributes);
		node.setOpen(true);
		node.setIconSkin((String) attributes.get("nodeType"));
		return node;
	}

	private List<String> getSystemId(List<ZtreeBean> ormSystems) {
		List<String> list = new ArrayList<String>();
		for (ZtreeBean sys : ormSystems) {
			list.add(sys.getId());
		}
		return list;
	}

	public void addResource(OrmResource ormResource) {
		ormResource.setResourceId(Identities.uuid2());
		if ("ROOT".equals(ormResource.getParentResId())) {
			ormResource.setRootResId(ormResource.getResourceId());
		} else {
			OrmResource parent = findOne(ormResource.getParentResId());
			ormResource.setRootResId(parent.getRootResId());
			ormResource.setSystemId(parent.getSystemId());
		}
		BigDecimal order = getMaxOrderNumber(ormResource.getParentResId());
		ormResource.setOrderNumber(order);
		add(ormResource);
	}

	private BigDecimal getMaxOrderNumber(String parentResId) {
		OrmResource ormResource = ormResourceDao.getMaxOrderNumber(parentResId);
		BigDecimal order = ormResource.getOrderNumber();
		if (null == order) {
			order = new BigDecimal(0);
		}
		return order.add(new BigDecimal(1));
	}

	public void updateResource(OrmResource ormResource, List<String> updateField) {
		updateSome(ormResource, updateField);
	}
	
	public void adjustmentOrder(String sourceId, String targetId, String moveType) {
		OrmResource targ = findOne(targetId);
		String pid = targ.getParentResId();
		BigDecimal tarorder = targ.getOrderNumber();
		OrmResource sour = findOne(sourceId);
		BigDecimal sourorder = sour.getOrderNumber();
		int result = tarorder.compareTo(sourorder);
		if (1 == result) {// 目标的序号比源的序号大
			ormResourceDao.substractOrderNumber(sourorder, tarorder, pid);
			if ("prev".equals(moveType)) {
				ormResourceDao.updateOrderNumber(tarorder.subtract(new BigDecimal(1)), sourceId);
			} else if ("next".equals(moveType)) {
				ormResourceDao.updateOrderNumber(tarorder, sourceId);
				ormResourceDao.updateOrderNumber(tarorder.subtract(new BigDecimal(1)), targetId);
			}
		} else if (-1 == result) {// 目标的序号比源的序号小
			ormResourceDao.addOrderNumber(tarorder,sourorder, pid);
			if ("prev".equals(moveType)) {
				ormResourceDao.updateOrderNumber(tarorder, sourceId);
				ormResourceDao.updateOrderNumber(tarorder.add(new BigDecimal(1)), targetId);
			} else if ("next".equals(moveType)) {
				ormResourceDao.updateOrderNumber(tarorder.add(new BigDecimal(1)), sourceId);
			}
		} else {// 目标的序号和源的序号相等
			if ("prev".equals(moveType)) {
				ormResourceDao.updateOrderNumber(tarorder.subtract(new BigDecimal(1)), sourceId);
			} else if ("next".equals(moveType)) {
				ormResourceDao.updateOrderNumber(tarorder.add(new BigDecimal(1)), sourceId);
			}
		}
	}

	public void deleteResource(String id) {
		List<String> list = new ArrayList<String>();
		list.add(id);
		List<OrmResource> children = ormResourceDao.getAllChildrenNode(list);
		List<String> ids=getResId(children);
		delete(ids);
		ormRoleResourceRightDao.deleteByResourceId(id);
	}

	private List<String> getResId(List<OrmResource> children) {
		List<String> ids = new ArrayList<String>();
		for(OrmResource child:children){
			ids.add(child.getResourceId());
		}
		return ids;
	}

	public void deleteResource(ArrayList<String> idArray) {
		List<OrmResource> children = ormResourceDao.getAllChildrenNode(idArray);
		List<String> ids=getResId(children);
		delete(ids);
		ormRoleResourceRightDao.deleteByResourceIdIn(idArray);
	}

	public void deleteBySystemId(String systemId) {
		ormResourceDao.deleteBySystemId(systemId);
		ormRoleResourceRightDao.deleteBySystemId(systemId);
	}

	public OrmResource findParentNode(String parentId, String parentType) {
		OrmResource res = new OrmResource();
		if ("SYSTEM".equals(parentType)) {
			res.setParentResId("ROOT");
			res.setSystemId(parentId);
		} else {
			res.setParentResId(parentId);
		}
		return res;
	}

}
