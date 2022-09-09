package com.dps_admin.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dps_admin.bean.DaysEnum;

@Entity
public class Holiday {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String festivals;
	
	@Enumerated(EnumType.STRING)
	private DaysEnum days;
	private String date;
	private String createdAt;
	private String updatedAt;
	private String note;
	private boolean isActive;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFestivals() {
		return festivals;
	}
	public void setFestivals(String festivals) {
		this.festivals = festivals;
	}
	public DaysEnum getDays() {
		return days;
	}
	public void setDays(DaysEnum days) {
		this.days = days;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	

}
