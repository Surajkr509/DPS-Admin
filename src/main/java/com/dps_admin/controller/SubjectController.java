package com.dps_admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dps_admin.bean.NotificationsEnum;
import com.dps_admin.model.Notifications;
import com.dps_admin.model.Student;
import com.dps_admin.model.Subjects;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.StudentRepository;
import com.dps_admin.repository.SubjectRepository;
import com.dps_admin.service.SubjectService;
import com.dps_admin.utils.Constants;

@Controller
@RequestMapping("/admin")
public class SubjectController {
	
	@Autowired
	SubjectService subjectService;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	NotificationRepository notificationRepository;
	
	@GetMapping("/addSubject")
	public String addSubject(Subjects subjects) {
		return "/subject/addSubject";
	}
	
	@PostMapping("/saveSubject")
	public String saveSubject(Subjects subjects,BindingResult bindingResult,RedirectAttributes rA) {
		System.err.println(":::::SubjectController:saveSubject:");
		try {
			
			if(subjects.getSubjectName()!=null) {
				System.err.println("fgdfg");
				Subjects data = subjectRepository.findBySubjectName(subjects.getSubjectName());
				System.err.println(data);
				if(data!=null) {
					System.err.println("gfhgfhgf");
					bindingResult.rejectValue("subjectName", "errors.subjects.subjectName", "Enter Subject-Name Already Exists");
				} else {
					subjectService.saveSubject(subjects);
					Notifications notifications = new Notifications(0,0,0,subjects.getId(),NotificationsEnum.Student_SIGNUP,
							"A new Subject has been Added :" +subjects.getSubjectName(), Constants.getDateAndTime(), Constants.getDateAndTime());
					notificationRepository.save(notifications);
					return "redirect:/admin/subjectList";
				}
			} else {
				return "/subject/addSubject";
			}
		} catch(Exception e){
			
		}
		return "/subject/addSubject";
	}

	@GetMapping("/subjectList")
	public String subjectList(Model model) {
		System.out.println("::::::SubjectController:subjectList::::");
		List<Subjects> subjectData=subjectRepository.findAll();
		model.addAttribute("subjectDetails", subjectData);
		return "/subject/subjectList";
	}
	
	@GetMapping("/editSubject/{id}")
	public ModelAndView editStudent(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/subject/editSubject");
		Subjects subjectData = subjectService.getById(id);
		if (subjectData == null)
			/* to pass the getStudents need to add RedirectAttribute and pass all getStudent fields null*/
			return null;
		modelAndView.addObject("subjectinfo",subjectData);
		return modelAndView;
	}
	@PostMapping("/updateSubject")
	public String updateSubject (Subjects subjects) {
	 subjectService.updateSubject(subjects);
	 return "redirect:/admin/subjectList";
	}
	@GetMapping("/deleteSubject/{id}")
	public String deleteSubject (@PathVariable Long id) {
		subjectRepository.deleteById(id);
		return "redirect:/admin/subjectList";
	}
	
	@GetMapping(value = "/updateSubjectStatus/{id}")
	@ResponseBody
	public String updateSubjectStatus(@PathVariable("id") Long id) {
		System.err.println("::: SubjectController.updateSubjectStatus :::");
		Subjects subjects=subjectRepository.findById(id).orElse(null);
		if(subjects!=null) {
			if(subjects.isStatus()) 
				subjects.setStatus(false);
			 else 
				 subjects.setStatus(true);
			subjectRepository.save(subjects);
	}
		return "Subject Status Updated";
	}
}
