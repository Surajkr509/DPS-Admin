package com.dps_admin.bean;

public enum NotificationsEnum {
	
	Student_SIGNUP("STUDENT_SIGNUP"),Teacher_SIGNUP("Teacher_SIGNUP");
	private String type;

	private NotificationsEnum() {
		
	}

	private NotificationsEnum(String type) {
		this.type = type;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
