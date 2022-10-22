package com.dps_admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps_admin.repository.ContentRepository;
import com.dps_admin.utils.Constants;

@Service
public class ContentService {
	
	@Autowired
	private ContentRepository contentRepository;

	public Object getCMSData() {
		List<String> data = new ArrayList<>();
		data.add(Constants.ABOUT_US);
		data.add(Constants.PRIVACY_POLICY);
		data.add(Constants.TERM_CONDITION);
		data.add(Constants.ADVANTAGES);
		data.add(Constants.FEATURES);
		data.add(Constants.EMAIL);
		data.add(Constants.COMPANY_ADDRESS);
		data.add(Constants.FAQ);
		data.add(Constants.DIRECTION_LINK);
		data.add(Constants.MAINCONTENT);
		return data;
	}

}
