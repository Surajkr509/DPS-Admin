package com.dps_admin.bean;

public enum DaysEnum {
	Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday;
	private String type;
	
	
	private DaysEnum() {
	}

	private DaysEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
