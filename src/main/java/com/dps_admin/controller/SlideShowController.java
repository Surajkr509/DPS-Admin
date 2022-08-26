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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dps_admin.bean.NotificationsEnum;
import com.dps_admin.model.Notifications;
import com.dps_admin.model.SlideShow;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.SlideShowRepository;
import com.dps_admin.service.SlideShowService;
import com.dps_admin.utils.Constants;

@Controller
@RequestMapping("/admin")
public class SlideShowController {

	@Autowired
	SlideShowService slideShowService;
	@Autowired
	SlideShowRepository slideShowRepository;  
	@Autowired
	NotificationRepository notificationRepository;

	@GetMapping("/addImage")
	public String addImage(SlideShow slideShow) {
		return "/slideshow/addImage";
	}

	@PostMapping("/addImage")
	public Object addImage(@Valid SlideShow slideShow,BindingResult bindingResult, @RequestParam("file") MultipartFile multipartFile,
			RedirectAttributes rA) {
		System.out.println("::SlideShowController.addImage::::");
		try {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("FileName:::"+fileName);
			slideShow.setPhotos(fileName);
			slideShow.setCreatedAt(Constants.getDateAndTime());
			slideShow.setUpdatedAt(Constants.getDateAndTime());
			slideShowService.add(slideShow, multipartFile);
			Notifications notifications = new Notifications(slideShow.getId(), 0, NotificationsEnum.SlideShow_Image,
					"A new SlideShow Image has added ", Constants.getDateAndTime(), Constants.getDateAndTime());
			notificationRepository.save(notifications);
			return "redirect:/admin/imageList";

		} catch (Exception e) {

		}
		return "/slideshow/addImage";
	}
	
	@GetMapping("/imageList")
	public String imageList(Model model) {
		List<SlideShow> imageData =slideShowRepository.findAll();
		model.addAttribute("imageDetails",imageData);
		return "slideshow/imageList";
	}
	@GetMapping("/editImage/{id}")
	public ModelAndView editImage(@PathVariable Long id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/slideshow/editImage");
		SlideShow data = slideShowService.getById(id);
		if(data == null)
			return null;
		model.addObject("imageinfo",data);
		return model;
	}
	
	
	@GetMapping("/viewImage/{id}")
	public ModelAndView viewImage(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/slideshow/viewImage");
		SlideShow data = slideShowService.getById(id);
		if (data == null)
			/* to pass the getStudents need to add RedirectAttribute and pass all getStudent fields null*/
			return null;

		modelAndView.addObject("imagedetails", data);

		return modelAndView;
	}
	@GetMapping("/deleteImage/{id}")
	public String delete (@PathVariable Long id) {
		slideShowService.delete(id);
		return "redirect:/admin/imageList";
	}
	
	@PostMapping("/updateImage")
	public String updateImage(@ModelAttribute SlideShow slideshow,RedirectAttributes rA,@RequestParam("image") MultipartFile multipartFile ) throws IOException {
		
		slideShowService.update(slideshow,multipartFile);
		return "redirect:/admin/imageList";
	}

}
