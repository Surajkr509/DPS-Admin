package com.dps_admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dps_admin.model.Notifications;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {

	List<Notifications> findAllByReadStatus(int i);

	Long countAllByReadStatus(int i);

}
