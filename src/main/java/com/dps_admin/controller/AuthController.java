package com.dps_admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.bean.ChangePassword;
import com.dps_admin.bean.ResultDTO;
import com.dps_admin.model.Admin;
import com.dps_admin.repository.AdminRepository;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.RoleRepository;
import com.dps_admin.repository.StudentRepository;
import com.dps_admin.repository.TeacherRepository;
import com.dps_admin.service.AdminDetailsImpl;
import com.dps_admin.service.AuthService;
import com.dps_admin.utils.BeanValidator;
import com.dps_admin.utils.Utility;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	@Autowired
	AuthService authService;
	@Autowired
	BeanValidator beanValidator;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	TeacherRepository teacherRepo;
	@Autowired
	StudentRepository studentRepo;
	@Autowired
	NotificationRepository notificationRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	JavaMailSender javaMail;
	
	@GetMapping("/signUp")
	public String signUp(Admin admin) {
		return "signup";
	}
	@GetMapping(value = "/default/index")
	public ModelAndView dashBoard(HttpServletRequest request, Authentication auth) {
		System.err.println("::: AdminController.dashBoard :::");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userData",auth.getName());
		modelAndView.setViewName("/index");
		return modelAndView;
	}
	
	@GetMapping("/login")
		public String signIn(Admin admin) {
			return "signin";
		}
	
	@PostMapping("/doLogin")
	public ModelAndView login(@Valid Admin admin,BindingResult bindingResult,Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView();
		Admin adminData=adminRepo.findByEmail(admin.getEmail());
		try {
			System.err.println(":::Auth. Login ::: "+authentication.getName());
			String email = admin.getEmail();
			if (authService.checkEmail(email) == false) {
				bindingResult.rejectValue("email", "errors.admin.email", "*Enter email address does not exist");
				modelAndView.setViewName("/signin");
				return modelAndView;
			}
			String password = admin.getPassword();
			if (authService.checkPassword(password) == false) {
				bindingResult.rejectValue("password", "errors.admin.password", "*Please Enter correct password");
				modelAndView.setViewName("/signin");
				return modelAndView;
			}
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword()));
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			AdminDetailsImpl userDetails=(AdminDetailsImpl)authentication.getPrincipal();
			modelAndView.addObject("userData",adminData.getName());
			Long teacherCount=teacherRepo.count();
			Long studentCount=studentRepo.count();
			Long noitfyCount=notificationRepo.count();
			Long roleCount=roleRepo.count();
			modelAndView.addObject("tC",teacherCount);
			modelAndView.addObject("sC",studentCount);
			modelAndView.addObject("nC",noitfyCount);
			modelAndView.addObject("rC",roleCount);
			modelAndView.addObject("msg","Admin login Successfully");
			modelAndView.setViewName("/index");
			return modelAndView;
		}catch (Exception e) {
		e.printStackTrace();
		
		}
		modelAndView.setViewName("/signin");
		return modelAndView;
	}
	@GetMapping("/forgotPassword")
	public String forgotPassword(Admin admin) {
		return "forgotPassword";
	}
	@PostMapping("/forgotPassword")
	public ModelAndView forgotPassword(String email,HttpServletRequest request){
		System.err.println("::::Auth.forgotPassword::::");
		ModelAndView model = new ModelAndView();
		ResultDTO<?> responsePacket=null;
		try {
			Admin data=adminRepo.findByEmail(email);
			if(data!=null) {
				authService.forgotPassword(data);
				System.err.println("OTP::::"+data.getOtp());
				String recipientEmail=Utility.getSiteURL(request)+ "/auth/changePassword?token=" +data.getOtp();
				sendEmail(data.getEmail(),recipientEmail,data.getOtp(),data.getName());
				model.addObject("msg1","OTP sent Successfully" );
				model.setViewName("/changePassword");
				return model;
			} else {
				model.addObject("msg2","*Enter email not exist" );
				model.setViewName("/forgotPassword");
				return model;
			} 
		}catch (Exception e) {
			e.printStackTrace();
			responsePacket=new ResultDTO<>(false,e.getMessage());
			model.addObject(responsePacket);
			model.setViewName("/forgotPassword");
			return model;
		}
	}
	@GetMapping("/changePassword")
	public String changePassword(Admin admin) {
		return "changePassword";
	}
	
	@PostMapping("/changePassword")
	public ModelAndView changePassword(ChangePassword changePassword) {
		System.err.println("Auth.changePassword::::");
		ModelAndView model = new ModelAndView();
		ResultDTO<?> responsePacket=null;
		try {
			Admin data=adminRepo.findByOtp(changePassword.getOtp());
			if(data!=null) {
				String otp=data.getOtp();
				if(changePassword.getOtp().equals(otp)) {
					if(changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
						data.setPassword(changePassword.getNewPassword());
						adminRepo.save(data);
						model.addObject("msg1","Password Changed Successfully");
						model.setViewName("/signin");
						return new ModelAndView("redirect:/auth/login");
					} else {
						model.addObject("msg2","*Password does not matched");
						model.setViewName("/changePassword");
						return model;
					}
				}else {
					model.addObject("msg2","*Please Enter Correct OTP.");
					model.setViewName("/changePassword");
					return model;
				}
			}else {
				model.addObject("msg2","*Please Enter Correct OTP.");
				model.setViewName("/changePassword");
				return model;
			}
		}catch(Exception e) {
			e.printStackTrace();
			responsePacket=new ResultDTO<>(false,e.getMessage());
			model.addObject(responsePacket);
			model.setViewName("/changePassword");
			return model;
		}
	}
	public void sendEmail (String recipientEmail, String link, String userOtp, String usersName)
			throws MessagingException, UnsupportedEncodingException {
			MimeMessage message = javaMail.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("contact@brsoftech.org", "BR Support");
			helper.setTo(recipientEmail);

			String subject = "Heres the link to reset your password";

			String content = "<p>Hello, " + usersName + "</p>"
			+ "<p>You have requested to reset your password. Please click the below link to reset your password</p>"
			+ "<p>Your Otp is " + userOtp + "</p>"
			+ "<p>Click the link below to change your password:</p>"
			+ "<p><a href=\"" + link + "\">Change my password</a></p>"
			
			+ "<p>Ignore this email if you do remember your password, "
			+ "or you have not made the request.</p>"
			+"<p>Thanks & Regards</p>"
			+"BR Support";

			helper.setSubject(subject);

			helper.setText(content, true);

			javaMail.send(message);
			}
	@GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response,Model model) {   
		System.err.println("LOGOUT::::::::::::");
		Cookie rememberMeCookie = new Cookie("remember-me", "");
		 rememberMeCookie.setMaxAge(0);
		 response.addCookie(rememberMeCookie);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
        }
         model.addAttribute("logout","You have been Logout Successfully!"); 
        return "redirect:/auth/login";
    }
	
	
}
