package com.dps_admin.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.bean.PeriodsEnum;
import com.dps_admin.repository.SubjectRepository;

@Controller
@RequestMapping("/admin")
public class RoutineController {
	
	@Autowired
	SubjectRepository subjectRepository;
	
	
	@GetMapping("/routine")
	public ModelAndView routine() {
		ModelAndView model = new ModelAndView();
		List<PeriodsEnum> enumValues = Arrays.asList(PeriodsEnum.values());
		model.setViewName("/routine/TimeTable");
		model.addObject("subject",subjectRepository.findAll());
		//model.addObject("subjects",PeriodsEnum.class);
		return model;
	}
}
