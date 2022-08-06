package com.dps_admin.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dps_admin.bean.NotificationsEnum;
import com.dps_admin.model.Notifications;
import com.dps_admin.model.Teacher;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.TeacherRepository;
import com.dps_admin.service.TeacherService;
import com.dps_admin.utils.Constants;

@Controller
@RequestMapping("/admin")
public class TeacherController {
	
	@Autowired
	TeacherService teacherService;
	@Autowired
	TeacherRepository teacherRepo;
	@Autowired
	NotificationRepository notificationRepo;
	
	@GetMapping("/addTeacher")
	public Object goToTeacher(Teacher teacher) {
		return "/teachers/addTeacher";
	}
	@PostMapping("/addTeacher")
	public Object addTeacher(@Valid Teacher teacher,BindingResult bindingResult,@RequestParam("image") MultipartFile multipartFile,RedirectAttributes rA) throws IOException {
		System.out.println("::Admin.AddTeacherController::::");
		try {
			String name = teacher.getName();
			if(name!=null) {
				
			}
			String mobileNo=teacher.getMobileNo();
			if(teacherService.checkMob(mobileNo)==true) {
				bindingResult.rejectValue("mobileNo", "errors.student.mobileNo", "Enter mobile no. address already exists");
			}
			String email=teacher.getEmail();
			if(teacherService.checkEmail(email)==true) {
				bindingResult.rejectValue("email", "errors.student.email", "Enter email address already exist");
			}
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			teacher.setPhotos(fileName);
			teacher.setUsername(email);
			teacher.setPassword(Constants.getRandomPassword());
			teacher.setCreatedAt(Constants.getDateAndTime());
			teacher.setUpdatedAt(Constants.getDateAndTime());
			teacherService.addTeacher(teacher,multipartFile);
			Notifications notifications=new Notifications(0,teacher.getId(),NotificationsEnum.Teacher_SIGNUP,"A new teacher has SignUp :"+teacher.getName(),Constants.getDateAndTime(),Constants.getDateAndTime());
			notificationRepo.save(notifications);
			return "redirect:/admin/teacherList";
		} catch (Exception e) {
			
		}
		return "/teachers/addTeacher";
	}
	
	@GetMapping("/teacherList")
	public String teacherList(Model model) {
		List<Teacher> teacherList = teacherRepo.findAll();
		model.addAttribute("teacherDetails",teacherList);
		return "teachers/teacherList";
	}
	@GetMapping("/viewTeacher/{id}")
	public ModelAndView viewTeacher(@PathVariable Long id) {
		System.err.println(":::TeacherController.View");
		ModelAndView model = new ModelAndView();
		model.setViewName("/teachers/viewTeacher");
		Teacher data=teacherService.getById(id);
		if(data==null) 
			return null;
		model.addObject("teacherdetails", data);
		return model;
	}
	@GetMapping("/editTeacher/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		System.err.println("TeacherController.edit:::");
		ModelAndView model = new ModelAndView();
		model.setViewName("/teachers/editTeacher");
		Teacher data = teacherService.getById(id);
		if(data==null)
			return null;
		model.addObject("teacherinfo",data);
		return model;
	}
	
	@PostMapping("/updateTeacher")
	public String updateTeacher(Teacher teacher,RedirectAttributes rA,@RequestParam("image") MultipartFile multipartFile) throws IOException {
	System.out.println("TeacherController.UpdateTeacher::::");	
	teacherService.update(teacher,multipartFile);
	return "redirect:/admin/teacherList";
	}
	
	
	@GetMapping("/teacher/{id}")
	public String delete (@PathVariable Long id) {
		teacherRepo.deleteById(id);
		return "redirect:/admin/teacherList";
	}
	@GetMapping(value = "/updateTeacherStatus/{id}")
	@ResponseBody
	public String updateTeacherStatus(@PathVariable("id") Long id) {
		System.err.println("::: TeacherController.updateTeacherStatus :::");
		Teacher teacher=teacherRepo.findById(id).orElse(null);
		if(teacher!=null) {
			if(teacher.isActive()) 
				teacher.setActive(false);
			 else 
				 teacher.setActive(true);
			teacherRepo.save(teacher);
	}
		return "Teacher Status Updated";
	}
}
