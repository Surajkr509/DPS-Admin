package com.dps_admin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "content_info")
public class ContentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Lob
	private String mainContent;

	@Lob
	private String communication;

	@Lob
	private String about;

	@Lob
	private String feature;

	@Lob
	private String advantage;

	@Lob
	private String pricing;

	@Lob
	private String fAQ;

	@Lob
	private String contact;

	private String email;

	private String companyLocation;

	private String directionLink;

	private String linkedinLink;

	private String youtubeLink;

	private String instagramLink;

	private String twitterLink;

	private String facebookLink;

	private String mobNo;

	@Lob
	private String termCondition;

	@Lob
	private String privacyPolicy;

	private String cmsType;

	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}

	public String getCommunication() {
		return communication;
	}

	public void setCommunication(String communication) {
		this.communication = communication;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getPricing() {
		return pricing;
	}

	public void setPricing(String pricing) {
		this.pricing = pricing;
	}

	public String getfAQ() {
		return fAQ;
	}

	public void setfAQ(String fAQ) {
		this.fAQ = fAQ;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyLocation() {
		return companyLocation;
	}

	public void setCompanyLocation(String companyLocation) {
		this.companyLocation = companyLocation;
	}

	public String getDirectionLink() {
		return directionLink;
	}

	public void setDirectionLink(String directionLink) {
		this.directionLink = directionLink;
	}

	public String getTermCondition() {
		return termCondition;
	}

	public void setTermCondition(String termCondition) {
		this.termCondition = termCondition;
	}

	public String getPrivacyPolicy() {
		return privacyPolicy;
	}

	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}

	public String getCmsType() {
		return cmsType;
	}

	public void setCmsType(String cmsType) {
		this.cmsType = cmsType;
	}

	public String getLinkedinLink() {
		return linkedinLink;
	}

	public void setLinkedinLink(String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String getInstagramLink() {
		return instagramLink;
	}

	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

}
