package cn.innosoft.fw.orm.server.common.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class Pagination<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// @JsonView(Views.List.class)
	private List<T> list;
	
	// @JsonView(Views.List.class)
	private int totalPages;
	
	// @JsonView(Views.List.class)
	private int currentPage;
	
	public Pagination() {
		
	}
	
	public Pagination(Page<T> page) {
		this.list = page.getContent();
		this.totalPages = page.getTotalPages();
		this.currentPage = page.getNumber() + 1;
	}
	
	public Pagination(List<T> list, int totalPages, int currentPage) {
		this.list = list;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
