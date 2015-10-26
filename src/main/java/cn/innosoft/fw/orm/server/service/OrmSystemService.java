package cn.innosoft.fw.orm.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.innosoft.fw.biz.base.querycondition.FilterGroup;
import cn.innosoft.fw.biz.base.querycondition.QueryConditionHelper;
import cn.innosoft.fw.biz.base.web.PageRequest;
import cn.innosoft.fw.biz.base.web.PageResponse;
import cn.innosoft.fw.biz.core.persistent.BaseDao;
import cn.innosoft.fw.biz.core.service.AbstractBaseService;
import cn.innosoft.fw.orm.server.model.OrmSystem;
import cn.innosoft.fw.orm.server.persistent.OrmCodeDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgRoleMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmOrgUserMapDao;
import cn.innosoft.fw.orm.server.persistent.OrmResourceDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleCodeRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleOrgRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmRoleResourceRightDao;
import cn.innosoft.fw.orm.server.persistent.OrmSystemDao;
import cn.innosoft.fw.orm.server.persistent.OrmUserRoleMapDao;

@Service
public class OrmSystemService extends AbstractBaseService<OrmSystem, String> {

	@Autowired
	private OrmSystemDao ormSystemDao;
	@Autowired
	private OrmCodeDao ormCodeDao;
	@Autowired
	private OrmOrgRoleMapDao ormOrgRoleMapDao;
	@Autowired
	private OrmOrgUserMapDao ormOrgUserMapDao;
	@Autowired
	private OrmResourceDao ormResourceDao;
	@Autowired
	private OrmRoleCodeRightDao ormRoleCodeRightDao;
	@Autowired
	private OrmRoleDao ormRoleDao;
	@Autowired
	private OrmRoleOrgRightDao ormRoleOrgRightDao;
	@Autowired
	private OrmRoleResourceRightDao ormRoleResourceRightDao;
	@Autowired
	private OrmUserRoleMapDao ormUserRoleMapDao;

	@Override
	public BaseDao<OrmSystem, String> getBaseDao() {
		return ormSystemDao;
	}

	/**
	 * 代码系统
	 * 
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<OrmSystem> find(PageRequest pageRequest) {
		FilterGroup group = QueryConditionHelper.add(pageRequest.getFilterGroup(), new String[] { "validSign" },
				new String[] { "Y" }, new String[] { "equal" });
		PageResponse<OrmSystem> page = findAll(group, pageRequest);
		return page;
	}

	public void addSystem(OrmSystem system) {
		ormSystemDao.save(system);
	}

	public void updateSystem(OrmSystem system) {
		ormSystemDao.update(system);
	}

	public void deleteSystem(String systemId) {
		ormSystemDao.delete(systemId);
		ormCodeDao.deleteBySystemId(systemId);
		ormOrgRoleMapDao.deleteBySystemId(systemId);
		ormResourceDao.deleteBySystemId(systemId);
		ormRoleCodeRightDao.deleteBySystemId(systemId);
		ormRoleDao.deleteBySystemId(systemId);
		ormRoleOrgRightDao.deleteBySystemId(systemId);
		ormRoleResourceRightDao.deleteBySystemId(systemId);
		ormUserRoleMapDao.deleteBySystemId(systemId);
	}
}
