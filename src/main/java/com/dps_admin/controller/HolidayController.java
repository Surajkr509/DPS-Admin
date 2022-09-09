package com.dps_admin.controller;

import java.util.Arrays;
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

import com.dps_admin.bean.DaysEnum;
import com.dps_admin.model.Holiday;
import com.dps_admin.repository.HolidayRepository;
import com.dps_admin.service.HolidayService;

@Controller
@RequestMapping("/admin")
public class HolidayController {
	
	@Autowired
	HolidayService holidayService; 
	@Autowired
	HolidayRepository holiRepo;
	@GetMapping("/addHoliday")
	public ModelAndView addHoliday (Holiday holiday) {
		System.out.println("::HolidayController.addHoliday::::");
		ModelAndView model = new ModelAndView();
		List<DaysEnum> enumValues = Arrays.asList(DaysEnum.values());
		model.addObject("days",enumValues);
		model.setViewName("/Holiday/addHoliday");
		return model;
	}
	@PostMapping("/saveHoliday")
	public String saveHoliday(Holiday holiday,BindingResult bindingResult,Model model) {
		System.out.println("::::HolidayController.saveHoliday::::");
		try {
			if(holiday.getFestivals()!=null) {
				Holiday data = holiRepo.findByFestivals(holiday.getFestivals());
				if(data!=null) {
					List<DaysEnum> enumValues = Arrays.asList(DaysEnum.values());
					model.addAttribute("days", enumValues);
					bindingResult.rejectValue("festivals","errors.holiday.festivals","*Enter Festival Name Already Exist");
					
				} else {
					holidayService.saveHoliday(holiday);
					return "redirect:/admin/holidayList";
				}
			} else {
				return "/Holiday/addHoliday";
			}
		} catch (Exception e) {
		}
		return  "/Holiday/addHoliday";
	}
	@GetMapping("/holidayList")
	public ModelAndView holidayList (Holiday holiday) {
		System.out.println(":::HolidayController.holidayList::::");
		ModelAndView model = new ModelAndView();
		List<Holiday> data = holiRepo.findAll();
		model.setViewName("/Holiday/HolidayList");
		model.addObject("holidayList",data);
		return model;
	}
	@GetMapping("/editHoliday/{id}")
		public ModelAndView editHoliday(@PathVariable Long id) {
		System.out.println(":::HolidayController.editHoliday::::");
			ModelAndView model = new ModelAndView();
			model.setViewName("/Holiday/editHoliday");
			Holiday data = holidayService.getById(id);
			List<DaysEnum> enumValues = Arrays.asList(DaysEnum.values());
			model.addObject("dayDetails",enumValues);
			model.addObject("holidayInfo",data);
			return model;
		}
	@PostMapping("/updateHoliday")
	public String updateHoliday (Holiday holiday) {
		holidayService.updateHoliday(holiday);
		return "redirect:/admin/holidayList";
	}
	
	@GetMapping("/deleteHoliday/{id}")
	public String deleteHoliday(@PathVariable Long id) {
		 holiRepo.deleteById(id);
		 return "redirect:/admin/holidayList";
	}
	@GetMapping(value = "/updateHolidayStatus/{id}")
	@ResponseBody
	public String updateHolidayStatus(@PathVariable("id") Long id) {
		System.err.println(":::HolidayController.updateHolidayStatus :::");
		Holiday holiday=holiRepo.findById(id).orElse(null);
		if(holiday!=null) {
			if(holiday.isActive()) 
				holiday.setActive(false);
			 else 
				 holiday.setActive(true);
			holiRepo.save(holiday);
	}
		return "Holiday Status Updated";
	}

}
