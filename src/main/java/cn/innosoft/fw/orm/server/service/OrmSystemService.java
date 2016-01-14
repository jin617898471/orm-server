package cn.innosoft.fw.orm.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.exception.ObjectMessageException;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.biz.utils.Identities;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.persistent.OrmSystemDao;
import cn.innosoft.fw.orm.server.util.ExportJs;

@Service
public class OrmSystemService extends AbstractBaseService<OrmSystem, String> {

	@Autowired
	private OrmSystemDao ormSystemDao;
	@Autowired
	private OrmCodeService ormCodeService;
	@Autowired
	private OrmResourceService ormResourceService;
	@Autowired
	private OrmRoleService ormRoleService;

	@Override
	public BaseDao<OrmSystem, String> getBaseDao() {
		return ormSystemDao;
	}

	public void addSystem(OrmSystem ormSystem) {
		ormSystem.setSystemId(Identities.uuid2());
		checkSystem(ormSystem.getSystemCode(), ormSystem.getSystemId());
		ormSystem.setCreateDt(new Date());
		add(ormSystem);
		ormRoleService.addAdminRole(ormSystem.getSystemId());
	}

	public void updateSystem(OrmSystem ormSystem, List<String> updateField) {
		checkSystem(ormSystem.getSystemCode(), ormSystem.getSystemId());
		updateSome(ormSystem, updateField);
	}

	private void checkSystem(String systemCode, String systemId) {
		Integer cout = ormSystemDao.countBySystemCodeAndSystemIdNot(systemCode, systemId);
		if (cout > 0) {
			throw new ObjectMessageException("存在值为" + systemCode + "的系统");
		}
	}

	public void deleteSystem(String systemId) {
		delete(systemId);
		ormCodeService.deleteBySystemId(systemId);
		ormResourceService.deleteBySystemId(systemId);
		ormRoleService.deleteBySystemId(systemId);
	}

	public void deleteSystem(ArrayList<String> idArray) {
		for (int i = 0; i < idArray.size(); i++) {
			deleteSystem(idArray.get(i));
		}
	}

	/**
	 * 查询有权限管理的系统
	 * 
	 * @return
	 */
	public List<OrmSystem> getHasRight() {
		return findAll(null,"createDt","desc");
	}
	

	/**
	 * 查询所有的系统
	 * 
	 * @return
	 */
	public List<OrmSystem> getAll() {
		return findAll(null,"createDt","desc");
	}

	public String createJs() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("all", getAll());
		map.put("hasRight", getHasRight());
		return ExportJs.export(map);
	}
	
}
