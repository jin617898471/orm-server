package cn.innosoft.fw.orm.server.model;

import java.io.Serializable;

public class UserOrg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String institution;
	private String department;
	private String post;

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

}
