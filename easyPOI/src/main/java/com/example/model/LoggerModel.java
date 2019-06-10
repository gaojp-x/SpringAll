package com.example.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import cn.afterturn.easypoi.excel.annotation.Excel;

@TableName("Logger")
public class LoggerModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//name列名--needMerge是否需要纵向合并单元格--orderNum列的排序--isStatistics统计--suffix加后缀
	@Excel(name = "编号",needMerge=false ,orderNum = "0",isStatistics=true,suffix="")
	int id;
	@Excel(name = "类别")
	String type;
	//mergeVertical纵向合并值相同的列
	@Excel(name = "操作信息",mergeVertical=false)
	String information;
	@Excel(name = "类信息", width = 100)
	String classInfo;
	@Excel(name = "日期",importFormat = "yyyy-MM-dd HH:mm:ss",exportFormat="yyyy-MM-dd HH:mm:ss")
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
