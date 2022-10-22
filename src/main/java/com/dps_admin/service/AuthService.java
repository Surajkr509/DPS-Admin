package com.dps_admin.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dps_admin.bean.FileUploadUtil;
import com.dps_admin.model.Admin;
import com.dps_admin.model.Role;
import com.dps_admin.repository.AdminRepository;
import com.dps_admin.repository.RoleRepository;
import com.dps_admin.utils.Constants;

@Service
public class AuthService {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public Object signUp(Admin admin,MultipartFile multipartFile) throws IOException {
		Role role = roleRepository.findByRoleName("ADMIN");
		if (role != null) {
			admin.setRoleId(role);
			admin.setName(admin.getName());
			admin.setEmail(admin.getEmail());
			admin.setUsername(admin.getEmail());
			admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
			admin.setMobileNo(admin.getMobileNo());
			admin.setCreatedAt(Constants.getDateAndTime());
			Admin savesAdmin =adminRepo.save(admin);
			String uploadDir = "src/main/resources/static/images/" + savesAdmin.getId();
			FileUploadUtil.saveFile(uploadDir, admin.getPhotos(), multipartFile);
			HashMap<String, Object> adminData = new HashMap<>();
			adminData.put("username", admin.getEmail());
			adminData.put("password", admin.getPassword());
			return adminData;
		} else {
			throw new RuntimeException("Role is not exist");
		}
	}

	public boolean checkEmail(String email) {
		Optional<Admin> data=adminRepo.findByUsername(email);
				return data.isPresent();
	}

	public boolean checkPassword(String password) {
		Optional<Admin> data=adminRepo.findByPassword(password);
		return data.isPresent();
	}

	public String forgotPassword(Admin user) {
	     String otp=Constants.generateOTP();
	     System.err.println(":::OTP:::"+otp);
	     user.setOtp(otp);
	     
	     
	     HashMap<String, Object> userData=new HashMap<>();
	     userData.put("studentId", user.getId());
	     userData.put("otp", otp);
	     adminRepo.save(user);
	     return otp;
		}

}
