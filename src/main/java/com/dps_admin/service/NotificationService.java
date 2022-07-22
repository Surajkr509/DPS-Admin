package com.dps_admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.dps_admin.model.Notifications;
import com.dps_admin.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired 
	NotificationRepository notificationRepository;
	public Long countAllUnReadNotifications() {
		return notificationRepository.countAllByReadStatus(0);
	}
	public Object getAllUnReadNotifications(Authentication auth) {
		String username=auth.getName();
		List<Notifications> list = notificationRepository.findAllByReadStatus(0);
		List<Object> dataList = new ArrayList<Object>();
		for (Notifications notification : list) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", notification.getId());
			hashMap.put("message", notification.getMessage());
			hashMap.put("readStatus", notification.getReadStatus());
			hashMap.put("type", notification.getType());
			hashMap.put("typeId", notification.getTypeId());
			hashMap.put("createdAt", notification.getCreatedAt());
			hashMap.put("username", username);
			dataList.add(hashMap);
			
		}
		return dataList;
	}
	public Object getAllNotifications() {
		List<Notifications> list = notificationRepository.findAll();
		return list;
	}
	public Object getAllReadNotifications(HttpServletRequest http) {
		List<Notifications> list = notificationRepository.findAllByReadStatus(1);
		return list;
	}
	public Notifications getById(Long id) {
		Optional<Notifications> notify=notificationRepository.findById(id);
		return notify.isPresent()?notify.get():null;
	}

}
