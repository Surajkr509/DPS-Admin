package com.dps_admin.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Constants {
	
	public static final String emailAlreadyExist = "emailAlreadyExist";
	public static final String userNameAlreadyExist = "userNameAlreadyExist";
	public static final String mobileAlreadyExist = "mobileAlreadyExist";
	public static final String roleNotFound = "roleNotFound";
	public static final String badRequest = "badRequest";
	public static final String invalidData = "invalidData";
	public static final String requestSuccess = "requestSuccess";
	
	public static final String EMAIL = "email";
	public static final String COMPANY_ADDRESS = "companyAddress";
	public static final String DIRECTION_LINK = "directionLink";
	public static final String MOBILE_NO = "mobNo";
	public static final String FACEBOOK_LINK = "facebookLink";
	public static final String LINKEDIN_LINK = "linkedinLink";
	public static final String TWITTER_LINK = "twitterLink";
	public static final String INSTAGRAM_LINK = "instagramLink";
	public static final String YOUTUBE_LINK = "youtubeLink";
	public static final String MAINCONTENT = "mainContent";
	public static final String ABOUT_US = "aboutUs";
	public static final String FEATURES = "features";
	public static final String TERM_CONDITION = "termCondition";
	public static final String PRIVACY_POLICY = "privacyPolicy";
	public static final String ADVANTAGES = "advantages";
	public static final String FAQ = "FAQ";
	public static final String CONTACT_US = "contactUs";
	public static final String APP_CMS = "APP_CMS";
	public static final String WEBSITE_CMS = "WEBSITE_CMS";
	public static final String CREDIT = "CREDIT";
	public static final String DEBIT = "DEBIT";
	public static final String REWARD = "REWARD";
	public static final String LOYALTY = "LOYALTY";
	
	
	public static String getDateAndTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		return String.valueOf(df.format(new Date()));
	}

	public static String getRandomPassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@#!$_";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 8) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static String generateOTP() {
		Random rnd=new Random();
		int[] otp=rnd.ints(6,0,9).toArray();
		StringBuilder sb=new StringBuilder();
		sb.append(otp[0]);
		sb.append(otp[1]);
		sb.append(otp[2]);
		sb.append(otp[3]);
		sb.append(otp[4]);
		sb.append(otp[5]);
		return sb.toString();
	}
	public static String deleteMultiPartFile(String path, String fileName) {
		System.out.println("path for delete " + path);
		System.out.println("path for delete name " + path + fileName);
		File deletFile = new File(path + fileName);
		if (deletFile.delete()) {
			System.out.println("file deleted");
		} else {
			return "File not found";
		}
		return "File deleted Successfully";
	}
	
	public class query {
	public static final String DELETE_BY_ROLE_ID = "call DeleteRole(:roleId)";
	}

}
