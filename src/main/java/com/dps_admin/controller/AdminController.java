package com.dps_admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.model.Admin;
import com.dps_admin.repository.AdminRepository;
import com.dps_admin.service.AdminService;
import com.dps_admin.service.AuthService;
import com.dps_admin.utils.BeanValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	BeanValidator beanValidator;
	@Autowired
	AuthService authService;
	@Autowired
	AdminService adminService;
	
	@PostMapping("/addAdmin")
	public ModelAndView addAdmin(Admin admin){
		System.out.println("Auth.signUp:::::");
		ModelAndView model = new ModelAndView();
		try {
			ArrayList<String> errorList=beanValidator.userValidate(admin);
			if(errorList.size()!=0) {
				model.addObject("msg1",errorList);
				model.setViewName("/signup");
				return model;
			}
			if(adminRepo.existsByEmail(admin.getEmail())) {
				model.addObject("msg2","*Admin Already exist by email.");
				model.setViewName("/signup");
				return model;
			}
			if(adminRepo.existsByMobileNo(admin.getMobileNo())){
				model.addObject("msg3","*Admin Already exist by mobile number. ");
				model.setViewName("/signup");
				return model;
			}else {
				authService.signUp(admin);
				model.addObject("msg2","Admin Registered Successfully");
				model.setViewName("/signin");
				return model;
			}
		} catch(Exception e) {
			model.setViewName("/signup");
			return model;
		}
		
	}
	@GetMapping("/events")
	public String events() {
		return "events.html";
	}
	
	@GetMapping("/profile")
	public ModelAndView profile(Authentication auth) {
		System.out.println("::::Admin.Controller.Profile:::::");
		ModelAndView modelAndView = new ModelAndView();
		Admin adminDetails = adminRepo.findByEmail(auth.getName());
		modelAndView.addObject("admin",adminDetails);
		modelAndView.setViewName("/profile");
		return modelAndView;
	}
	
	@PostMapping("/updateProfile")
	public String updateProfile(Authentication auth,Admin admin,@RequestParam("image") MultipartFile multipartFile) throws IOException{
		System.out.println("::::AdminController.updateProfile:::::");
		adminService.updateProfile(admin,multipartFile);
		return "redirect:/admin/profile";
	}

}
