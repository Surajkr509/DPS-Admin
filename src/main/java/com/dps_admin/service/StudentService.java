package com.dps_admin.service;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dps_admin.bean.FileUploadUtil;
import com.dps_admin.model.Student;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.StudentRepository;
import com.dps_admin.utils.Constants;


@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	NotificationRepository notificationRepository;
	
	
	public Student getById(Long id) {
		Optional<Student> student= studentRepository.findById(id);
		return student.isPresent()?student.get():null;
	}
	
	public void delete(Long id) {
		Optional<Student> existStudent = studentRepository.findById(id);
		if (existStudent.isPresent())
			studentRepository.deleteById(id);
	}
	public Object update(Student student,MultipartFile multipartFile ) throws IOException {
		System.err.println("Update:::");
		Optional<Student> existStudent = studentRepository.findById(student.getId());
		if (existStudent.isPresent()) {
			Student data = existStudent.get();
			data.setName(student.getName());
			data.setStandard(student.getStandard());
			data.setEmail(student.getEmail());
			data.setMobileNo(student.getMobileNo());
		if(!multipartFile.isEmpty()) {
			String uploadDir2 = "src/main/resources/static/images/" + data.getId()+"/";
			
			Constants.deleteMultiPartFile(uploadDir2, data.getPhotos());
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("Image:::"+fileName);
			student.setRoleId(data.getRoleId());
			student.setPhotos(fileName);
			studentRepository.save(student);
			String uploadDir = "src/main/resources/static/images/" + data.getId();
			FileUploadUtil.saveFile(uploadDir, student.getPhotos(), multipartFile);
		}
			studentRepository.save(data);
		}
		return null;
	}

	public boolean checkMob(String mobileNo) {
		Boolean studentData = studentRepository.existsByMobileNo(mobileNo);
		return studentData;
	}

	public boolean checkEmail(String email) {
		Boolean studentData=studentRepository.existsByEmail(email);
		return studentData;
	}

	public Object adds(@Valid Student student, MultipartFile multipartFile) throws IOException {
//		Notifications notifications = new Notifications(student.getId(),0,NotificationsEnum.Student_SIGNUP,
//				"A new player has SignUp", Constants.getDateAndTime(), Constants.getDateAndTime());
		Student savedStudent = studentRepository.save(student);
		String uploadDir = "src/main/resources/static/images/" + savedStudent.getId();
		FileUploadUtil.saveFile(uploadDir, student.getPhotos(), multipartFile);
//		notificationRepository.save(notifications);
		return null;
	}

}
