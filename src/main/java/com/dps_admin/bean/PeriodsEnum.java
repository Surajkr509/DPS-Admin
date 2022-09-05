package com.dps_admin.bean;

public enum PeriodsEnum {
	I,II,III,IV,V,VI,VII,VIII;
	private String type;

	
	private PeriodsEnum() {
	}


	private PeriodsEnum(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


}
