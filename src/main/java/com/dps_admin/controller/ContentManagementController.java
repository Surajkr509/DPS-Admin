package com.dps_admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.repository.ContentRepository;
import com.dps_admin.service.ContentService;

@Controller
@RequestMapping("/admin")
public class ContentManagementController {
	
	@Autowired
	private ContentService contentService;
	@Autowired
	private ContentRepository contentRepository;
	
	@GetMapping(value = "/content")
	public ModelAndView viewContent(Authentication auth) {
		ModelAndView modelAndView = new ModelAndView();
				
		if (auth!=null) {
			modelAndView.addObject("content", contentService.getCMSData());
			//modelAndView.addObject("titleList", cmsService.getCMSContentList());
			modelAndView.setViewName("/Content");
			return modelAndView;
		} else {
			modelAndView.setViewName("admin/403");
			return modelAndView;
		}
	}

}
