package com.example.model;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String name;//角色名称
    private String memo;//角色描述
	public Role(Integer id, String name, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.memo = memo;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", memo=" + memo + "]";
	}

    
}
