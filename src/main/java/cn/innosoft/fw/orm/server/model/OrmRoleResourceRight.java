package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the ORM_ROLE_RESOURCE_RIGHT database table.
 * 
 */
@Entity
@Table(name="ORM_ROLE_RESOURCE_RIGHT")
@NamedQuery(name="OrmRoleResourceRight.findAll", query="SELECT o FROM OrmRoleResourceRight o")
public class OrmRoleResourceRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="RESOURCE_ID")
	private String resourceId;

	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="SYSTEM_ID")
	private String systemId;

	public OrmRoleResourceRight() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}