package com.municipalengineering.entity;

import java.io.Serializable;

public class IconBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String className;
	private int resId;

	public IconBean() {
	   //this is used by BeanUtils
	}

	public IconBean(String className, int resId) {
		this.className = className;
		this.resId = resId;
	}

	public String getClassName() {
		return className;
	}

	public int getResId() {
		return this.resId;
	}

}
