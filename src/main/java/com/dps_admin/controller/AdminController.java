package com.dps_admin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.model.Admin;
import com.dps_admin.repository.AdminRepository;
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
		return "events";
	}
	
	@GetMapping("/profile")
	public String profile() {
		//System.out.println("::::::"+authentication.getName());
		return "profile";
	}

}
