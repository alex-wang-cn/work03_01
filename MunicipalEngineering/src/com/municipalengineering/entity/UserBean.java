package com.municipalengineering.entity;

import java.io.Serializable;

import org.simpleframework.xml.Element;

public class UserBean implements Serializable {
	private static final long serialVersionUID = 774925424958942163L;
	private String passward;
	private String userName;
	private boolean isRemberPassWorld;
	private String userId;
	
	private String getContent;

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isRemberPassWorld() {
		return isRemberPassWorld;
	}

	public void setRemberPassWorld(boolean isRemberPassWorld) {
		this.isRemberPassWorld = isRemberPassWorld;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserBean [passward=" + passward + ", userName=" + userName
				+ ", isRemberPassWorld=" + isRemberPassWorld + ", userId="
				+ userId + "]";
	}

}
