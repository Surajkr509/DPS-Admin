package com.dps_admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps_admin.model.Notifications;
import com.dps_admin.repository.AdminRepository;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.RoleRepository;
import com.dps_admin.repository.StudentRepository;
import com.dps_admin.repository.TeacherRepository;

@Service
public class ChartService {

	@Autowired
	TeacherRepository teacherRepo;
	@Autowired
	StudentRepository studentRepo;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	NotificationRepository notificationRepo;
	@Autowired
	RoleRepository roleRepo;
	
	
	public Object getChartData() {
		HashMap<String, Object> data = new HashMap<>();
//		String t="Student_SIGNUP";
//		List<Notifications> tData=notificationRepo.findAllByType(t);
//		int tCount=tData.size();
//		System.err.println(":::FDFS"+tCount);
		data.put("totalStudents", studentRepo.count());
		data.put("totalTeachers", teacherRepo.count());
		data.put("totalAdmins", adminRepository.count());
		data.put("totalNotifys", notificationRepo.count());
		data.put("totalRoles", roleRepo.count());
	
		return data;
	}

}
