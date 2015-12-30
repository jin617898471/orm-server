package cn.innosoft.fw.orm.server.common.entity;

import java.io.Serializable;

/**
 * 返回数据实体
 */
public class InfoWrap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// @JsonView(Views.List.class)
	private String status;
	
	// @JsonView(Views.List.class)
	private String message;
	
	// @JsonView(Views.List.class)
	private Object data;

	public InfoWrap(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
