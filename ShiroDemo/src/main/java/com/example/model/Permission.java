package com.example.model;

import java.io.Serializable;

public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 private Integer id;
	 private String url;//URL地址
	 private String name;//URL描述
	public Permission(Integer id, String url, String name) {
		super();
		this.id = id;
		this.url = url;
		this.name = name;
	}
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", url=" + url + ", name=" + name + "]";
	}

	 
	 
}
