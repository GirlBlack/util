package com.sprucetec.tms.controller.export;

public class ColnameToField {
	private String colname;//列名
	
	private String fieldName;//类属性名
	
	public ColnameToField(String fieldName, String colname){
		this.colname = colname;
		this.fieldName = fieldName;
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
