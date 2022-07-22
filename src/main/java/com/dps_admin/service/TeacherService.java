package com.dps_admin.service;



import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dps_admin.bean.FileUploadUtil;
import com.dps_admin.model.Teacher;
import com.dps_admin.repository.TeacherRepository;
import com.dps_admin.utils.Constants;

@Service
public class TeacherService {

	@Autowired
	TeacherRepository teacherRepo;
	
	public Object getList() {
		return teacherRepo.findAll();
	}

	public boolean checkMob(String mobileNo) {
		Boolean mobData=teacherRepo.existsByMobileNo(mobileNo);
		return mobData;
	}

	public boolean checkEmail(String email) {
		Boolean emailData=teacherRepo.existsByEmail(email);
		return emailData;
	}

	public Object addTeacher(@Valid Teacher teacher, MultipartFile multipartFile) throws IOException {
		Teacher teacherData=teacherRepo.save(teacher);
		String uploadDir = "src/main/resources/static/images/" + teacherData.getId();
		FileUploadUtil.saveFile(uploadDir, teacher.getPhotos(), multipartFile);
		return null;
	}

	public Teacher getById(Long id) {
		Optional<Teacher> data=teacherRepo.findById(id);
		return data.isPresent()?data.get():null;
	}

	public Object update(Teacher teacher, MultipartFile multipartFile) throws IOException {
		Optional<Teacher> teacherData = teacherRepo.findById(teacher.getId());
		if(teacherData.isPresent()) {
			Teacher reqTeacher=teacherData.get();
			reqTeacher.setName(teacher.getName());
			reqTeacher.setClassTeacher(teacher.getClassTeacher());
			reqTeacher.setMobileNo(teacher.getMobileNo());
			reqTeacher.setEmail(teacher.getEmail());
			if(!multipartFile.isEmpty()) {
				String uploadDir2 = "src/main/resources/static/images/" + reqTeacher.getId()+"/";
				
				Constants.deleteMultiPartFile(uploadDir2, reqTeacher.getPhotos());
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println("Image:::"+fileName);
				teacher.setPhotos(fileName);
				teacherRepo.save(teacher);
				String uploadDir = "src/main/resources/static/images/" + reqTeacher.getId();
				FileUploadUtil.saveFile(uploadDir, teacher.getPhotos(), multipartFile);
				
			}
			
			
			teacherRepo.save(reqTeacher);
		}
		
		return null;
		
	}
	
	
}
