package com.dps_admin.service;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dps_admin.bean.FileUploadUtil;
import com.dps_admin.model.SlideShow;
import com.dps_admin.model.Student;
import com.dps_admin.repository.SlideShowRepository;
import com.dps_admin.utils.Constants;

@Service
public class SlideShowService {

	@Autowired
	SlideShowRepository slideShowRepository;
	public Object add(@Valid SlideShow slideShow, MultipartFile multipartFile) throws IOException {
		SlideShow slideData=slideShowRepository.save(slideShow);
		String uploadDir = "src/main/resources/static/images/" + slideData.getId();
		FileUploadUtil.saveFile(uploadDir, slideShow.getPhotos(), multipartFile);
//		notificationRepository.save(notifications);
		return null;
	}
	public SlideShow getById(Long id) {
		Optional<SlideShow> data = slideShowRepository.findById(id);
		return data.isPresent()?data.get():null;
	}
	public void delete(Long id) {
		Optional<SlideShow> data = slideShowRepository.findById(id);
		if(data.isPresent())
			slideShowRepository.deleteById(id);
	}
	public Object update(SlideShow slideshow, MultipartFile multipartFile) throws IOException {
		System.err.println("Update:::");
		Optional<SlideShow> existSlideShow = slideShowRepository.findById(slideshow.getId());
		System.out.println("Image ID" + slideshow);
		if (existSlideShow.isPresent()) {
			SlideShow data = existSlideShow.get();
			data.setImageName(slideshow.getImageName());
			data.setUpdatedAt(Constants.getDateAndTime());
		
		if(!multipartFile.isEmpty()) {
			String uploadDir2 = "src/main/resources/static/images/" + data.getId()+"/";
			
			Constants.deleteMultiPartFile(uploadDir2, data.getPhotos());
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("Image:::"+fileName);
			slideshow.setPhotos(fileName);
			slideShowRepository.save(slideshow);
			String uploadDir = "src/main/resources/static/images/" + data.getId();
			FileUploadUtil.saveFile(uploadDir, slideshow.getPhotos(), multipartFile);
		}
		slideShowRepository.save(data);
		}
		return null;

		
	}

}
