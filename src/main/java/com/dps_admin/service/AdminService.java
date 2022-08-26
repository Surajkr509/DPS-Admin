package com.dps_admin.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dps_admin.bean.FileUploadUtil;
import com.dps_admin.model.Admin;
import com.dps_admin.repository.AdminRepository;
import com.dps_admin.utils.Constants;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;

	public Object updateProfile(Admin admin, MultipartFile multipartFile) throws IOException {
		System.out.println(":::::ID::::"+admin.getId());
		System.out.println(":::::email::::"+admin.getEmail());
		Optional<Admin> adminData = adminRepository.findById(admin.getId());
		System.out.println(":::::ID::::"+admin.getId());
		if(adminData.isPresent()) {
			Admin adminDetails=adminData.get();
			adminDetails.setName(admin.getName());
			System.err.println("::::::Name::::::"+admin.getEmail());
			adminDetails.setAddress(admin.getAddress());
			adminDetails.setGender(admin.getGender());
			adminDetails.setCountry(admin.getCountry());
			adminDetails.setCity(admin.getCity());
			adminDetails.setState(admin.getState());
			adminDetails.setMobileNo(admin.getMobileNo());
			adminDetails.setPincode(admin.getPincode());
			adminDetails.setPassword(adminDetails.getPassword());
			adminDetails.setUsername(adminDetails.getUsername());
			adminDetails.setOtp(adminDetails.getOtp());
			adminDetails.setCreatedAt(adminDetails.getCreatedAt());
			adminDetails.setUpdatedAt(Constants.getDateAndTime());
			
			adminDetails.setDesignation(admin.getDesignation());
			if(!multipartFile.isEmpty()) {
				String uploadDir2 = "src/main/resources/static/images/" + adminDetails.getId()+"/";
				Constants.deleteMultiPartFile(uploadDir2, adminDetails.getPhotos());
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println("Image:::"+fileName);
				admin.setRoleId(adminDetails.getRoleId());
				admin.setPhotos(fileName);
				adminRepository.save(admin);
				String uploadDir = "src/main/resources/static/images/" + adminDetails.getId();
				FileUploadUtil.saveFile(uploadDir, admin.getPhotos(), multipartFile);
			}
			adminRepository.save(adminDetails);
		}
		
		return null;
	}

}
