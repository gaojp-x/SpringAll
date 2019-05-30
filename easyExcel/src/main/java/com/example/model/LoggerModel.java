package com.example.model;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class LoggerModel extends BaseRowModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelProperty(value= {"编号"},index=0)
	int id;
	@ExcelProperty(value= {"类型"},index=1)
	String type;
	@ExcelProperty(value= {"信息"},index=2)
	String information;
	@ExcelProperty(value= {"类信息"},index=3)
	String classInfo;
	@ExcelProperty(value= {"时间"},index=4)
	String dateTime;

	public LoggerModel() {
		super();
	}

	public LoggerModel(int id, String type, String information, String classInfo, String dateTime) {
		super();
		this.id = id;
		this.type = type;
		this.information = information;
		this.classInfo = classInfo;
		this.dateTime = dateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "LoggerModel [id=" + id + ", type=" + type + ", information=" + information + ", classInfo=" + classInfo
				+ ", dateTime=" + dateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classInfo == null) ? 0 : classInfo.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + id;
		result = prime * result + ((information == null) ? 0 : information.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoggerModel other = (LoggerModel) obj;
		if (classInfo == null) {
			if (other.classInfo != null)
				return false;
		} else if (!classInfo.equals(other.classInfo))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (id != other.id)
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
