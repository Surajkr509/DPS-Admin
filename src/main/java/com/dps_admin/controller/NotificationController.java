package com.dps_admin.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.model.Notifications;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.service.NotificationService;
import com.dps_admin.utils.Constants;

@Controller
public class NotificationController {
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	NotificationService notificationService;
	
	@GetMapping(value = "/getAllUnReadNotificationsCount")
	@ResponseBody
	public Long getAllUnReadNotificationsCount(Model model,Authentication auth) {
	 Long count=notificationService.countAllUnReadNotifications();
	 System.out.println(":::::::::::"+auth.getName());
	 model.addAttribute("count",count);
	 return count;
	}
	
	@GetMapping(value = "/getAllUnReadNotifications")
	@ResponseBody
	public Object getAllUnReadNotifications(Authentication auth) {
		return notificationService.getAllUnReadNotifications(auth);
	}

	@GetMapping("/getAllNotifications")
	public String getAllNotifications(Model model) {
		List<Notifications> notiList=notificationRepository.findAll();
	
		
		System.err.println("::::::"+notiList);
		model.addAttribute("notificationDetails", notiList);
		return "notifications/notificationList";
	}
	@GetMapping(value="/getAllReadNotifications")
	@ResponseBody
	public Object getAllReadNotification(HttpServletRequest http) {
		return notificationService.getAllReadNotifications(http);
	}
//	@GetMapping("/home")
//	public String getAllNotifications(Model model) {
//		 Long count=notificationsService.countAllUnReadNotifications();
//		 Object notifications=notificationsService.getAllUnReadNotifications();
//		 List<Players> playerdata=playersService.getAllPlayers();
//		 model.addAttribute("count",count);
//		 model.addAttribute("noticelist", notifications);
//		 model.addAttribute("playerslist", playerdata);
//		 return "index";
//	}
	@GetMapping("/getUnReadNotificationById/{id}")
	public ModelAndView getUnReadNotificationById(@PathVariable Long id) {
		System.err.println("NotificationController.UnReadNotiByID:::");
		ModelAndView modelAndView=new ModelAndView();
		if(id!=null) {
		Notifications notifyData=notificationRepository.findById(id).get();
		modelAndView.addObject("notifyDetails", notifyData);
		modelAndView.setViewName("/notifications/UnReadnotificationById");
		notifyData.setReadStatus(1);
		notificationRepository.save(notifyData);
		return modelAndView;
	} else {
		modelAndView.setViewName("Error");
		return modelAndView;
	}
	}
	@GetMapping("/editNotification/{id}")
	public ModelAndView editNotification(@PathVariable Long id) {
		System.err.println("::NotificationController.Edit::::::");
		ModelAndView model = new ModelAndView();
		model.setViewName("/notifications/editNotification");
		Notifications notiData=notificationService.getById(id);
		if(notiData==null) {
			model.setViewName("/notifications/notificationList");
			return model;
		}
		model.addObject("notifyInfo",notiData);
		return model;
	}
	@PostMapping("/updateNotify")
	public String updateNotify(Notifications notifications) {
		System.err.println("NotificationController.update::::");
		notifications.setUpdatedAt(Constants.getDateAndTime());
		notificationRepository.save(notifications);
		return "redirect:/getAllNotifications";
	}

}
