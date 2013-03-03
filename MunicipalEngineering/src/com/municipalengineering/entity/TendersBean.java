package com.municipalengineering.entity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;

public class TendersBean {

	@Element(name = "Id")
	private String id = "";
	@Element(name = "BId")
	private String bId = "";
	@Element(name = "BiaoduanName")
	private String name = "";
	@Element(name = "area")
	private String area = "";
	private String address = "";
	private String projectManager = "";
	private String projectManagerPhone = "";
	private String name2 = "";
	private String phone2 = "";
	private String gfZt = "";
	private String monitorType = "";
	private String monitorTypeGz = "";
	private String skjzt = "";
	private String spzt = "";
	private String tenderPersonName = "";
	private String tenderPersionPhone = "";
	private String comments = "";
	private List<VDTBean> vdts = new ArrayList<VDTBean>();

	public List<VDTBean> getVdts() {
		return vdts;
	}

	public void setVdts(List<VDTBean> vdts) {
		this.vdts = vdts;
	}

	public String getTenderPersonName() {
		return tenderPersonName;
	}

	public void setTenderPersonName(String tenderPersonName) {
		this.tenderPersonName = tenderPersonName;
	}

	public String getTenderPersionPhone() {
		return tenderPersionPhone;
	}

	public void setTenderPersionPhone(String tenderPersionPhone) {
		this.tenderPersionPhone = tenderPersionPhone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBId() {
		return bId;
	}

	public void setBId(String taskId) {
		this.bId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getProjectManagerPhone() {
		return projectManagerPhone;
	}

	public void setProjectManagerPhone(String projectManagerPhone) {
		this.projectManagerPhone = projectManagerPhone;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getGfZt() {
		return gfZt;
	}

	public void setGfZt(String gfZt) {
		this.gfZt = gfZt;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getMonitorTypeGz() {
		return monitorTypeGz;
	}

	public void setMonitorTypeGz(String monitorTypeGz) {
		this.monitorTypeGz = monitorTypeGz;
	}

	public String getSkjzt() {
		return skjzt;
	}

	public void setSkjzt(String skjzt) {
		this.skjzt = skjzt;
	}

	public String getSpzt() {
		return spzt;
	}

	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
