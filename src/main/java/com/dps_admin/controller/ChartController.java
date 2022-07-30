package com.dps_admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.service.ChartService;

@Controller
@RequestMapping("/admin")
public class ChartController {

	@Autowired
	ChartService chartService; 
	
	@GetMapping("/chart")
	public ModelAndView chart() {
		System.err.println("::::ChartController.chart:::::");
		ModelAndView model =new ModelAndView();
		model.addObject("data",chartService.getChartData());
		model.setViewName("/charts/pieChart");
		return model;
	}
}
