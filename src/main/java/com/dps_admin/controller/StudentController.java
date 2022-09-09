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
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.dps_admin.model.Student;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.StudentRepository;
import com.dps_admin.service.StudentService;
import com.dps_admin.utils.Constants;

@Controller
@RequestMapping("/admin")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepo;
	@Autowired
	StudentService studentService;
	@Autowired
	NotificationRepository notificationRepository;
	
	@GetMapping("/addStud")
	public String goToAddStudents(Student student) {
		return "/students/AddStud";
	}

	@PostMapping("/addStud")
	public Object addStudents(@Valid Student student, BindingResult bindingResult,
			@RequestParam("image") MultipartFile multipartFile,RedirectAttributes rA) throws IOException {
			System.err.println("Multipart::::"+multipartFile);
		try {
			System.err.println("::: AddSTud ::: ");
			String name = student.getName();
			if (name == null) {
				System.err.println("::: Checking Name ::: ");
			//	bindingResult.rejectValue("name", "errors.student.name", "Enter a valid name");
			}
			
			String mobileNo = student.getMobileNo();
			if (studentService.checkMob(mobileNo) == true) {
				bindingResult.rejectValue("mobileNo", "errors.student.mobileNo", "Enter mobile no. address already exists");
			}
		
			
			String email = student.getEmail();
			if (studentService.checkEmail(email) == true) {
				bindingResult.rejectValue("email", "errors.student.email", "Enter email address already exist");
			}
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			student.setPhotos(fileName);
			student.setPassword(Constants.getRandomPassword());
			studentService.adds(student, multipartFile);
			Notifications notifications = new Notifications(student.getId(),0,0,0,NotificationsEnum.Student_SIGNUP,
					"A new student has SignUp :"+student.getName(), Constants.getDateAndTime(), Constants.getDateAndTime());
			notificationRepository.save(notifications);
			rA.addAttribute("pageNumber", null);
			rA.addAttribute("pageSize", null);
			rA.addAttribute("sortBy", null);
			rA.addAttribute("sortDirection", null);
			return "redirect:/admin/studentList";
		} catch (Exception e) {

		}
		return "/students/AddStud";
	}

	
	@GetMapping("/studentList")
	public String studentList(Model model) {
		List<Student> studentData=studentRepo.findAll();
		System.out.println("StudentList::::"+studentData);
		model.addAttribute("studentDetails", studentData);
		return "students/studentList";
	}
	@GetMapping("/view/{id}")
	public ModelAndView viewStudent(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/students/viewStud");
		Student studentdata = studentService.getById(id);
		if (studentdata == null)
			/* to pass the getStudents need to add RedirectAttribute and pass all getStudent fields null*/
			return null;

		modelAndView.addObject("studentdetails", studentdata);

		return modelAndView;
	}
	@GetMapping("/{id}")
	public String delete(@PathVariable Long id) {
		studentService.delete(id);
		
		return "redirect:/admin/studentList";
	}
	@GetMapping("/edit/{id}")
	public ModelAndView editStudent(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/students/editStud");
		Student studentdata = studentService.getById(id);
		if (studentdata == null)
			/* to pass the getStudents need to add RedirectAttribute and pass all getStudent fields null*/
			return null;

		modelAndView.addObject("studentinfo",studentdata);

		return modelAndView;

	}
	@PostMapping("/update")
	public String updateStudent(@ModelAttribute Student student,RedirectAttributes rA,@RequestParam("image") MultipartFile multipartFile ) throws IOException {
		
		studentService.update(student,multipartFile);
		rA.addAttribute("pageNumber", null);
		rA.addAttribute("pageSize", null);
		rA.addAttribute("sortBy", null);
		rA.addAttribute("sortDirection", null);
		return "redirect:/admin/studentList";
	}
	
	@GetMapping(value = "/updateStudentStatus/{id}")
	@ResponseBody
	public String updateStudentStatus(@PathVariable("id") Long id) {
		System.err.println("::: StudentController.updateStudentStatus :::");
		Student student=studentRepo.findById(id).orElse(null);
		if(student!=null) {
			if(student.isActive()) 
				student.setActive(false);
			 else 
				 student.setActive(true);
			studentRepo.save(student);
	}
		return "Student Status Updated";
	}
	
}
