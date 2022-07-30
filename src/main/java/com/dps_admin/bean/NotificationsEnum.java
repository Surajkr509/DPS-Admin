package com.dps_admin.bean;

public enum NotificationsEnum {
	
	Student_SIGNUP("STUDENT_SIGNUP"),Teacher_SIGNUP("Teacher_SIGNUP"),SlideShow_Image("Image_Added");
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
