package com.municipalengineering.entity;

public class VDTBean {
	private String installPlace = "";
	private String id = "";

	public String getInstallPlace() {
		return installPlace;
	}

	public void setInstallPlace(String installPlace) {
		this.installPlace = installPlace;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VDTBean [installPlace=" + installPlace + ", id=" + id + "]";
	}

}
