package com.municipalengineering.entity;

import java.util.List;

public class CompanyBean {
	private String cId;
	private String CName;
	private List<String> onLineProjectName;

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getCName() {
		return CName;
	}

	public void setCName(String cName) {
		CName = cName;
	}

	public List<String> getOnLineProjectName() {
		return onLineProjectName;
	}

	public void setOnLineProjectName(List<String> onLineProjectName) {
		this.onLineProjectName = onLineProjectName;
	}

}
