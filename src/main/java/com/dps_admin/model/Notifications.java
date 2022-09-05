package com.dps_admin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import com.dps_admin.bean.NotificationsEnum;


@Entity
public class Notifications implements Serializable {
	public static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private long studentId;
	private long teacherId;
	private long slideshowId;
	private long subjectId;
	@Enumerated(EnumType.STRING)
	private NotificationsEnum type;
	
	private String typeId="0";
	private String message;
	private int readStatus=0;
	
	private String createdAt;
	
	private String updatedAt;
	private String denyReason="NA";

	public Notifications() {
	}

	public Notifications(long studentId, long teacherId,long slideshowId,long subjectId,NotificationsEnum type, String message, String createdAt, String updatedAt) {
		super();
		this.studentId = studentId;
		this.teacherId = teacherId;
		this.slideshowId = slideshowId;
		this.subjectId = subjectId;
		this.type = type;
		this.message = message;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}
	

	public long getSlideshowId() {
		return slideshowId;
	}

	public void setSlideshowId(long slideshowId) {
		this.slideshowId = slideshowId;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public NotificationsEnum getType() {
		return type;
	}

	public void setType(NotificationsEnum type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}

	public String getDenyReason() {
		return denyReason;
	}

	public void setDenyReason(String denyReason) {
		this.denyReason = denyReason;
	}
	

}
