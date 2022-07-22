package com.dps_admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dps_admin.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

	Boolean existsByMobileNo(String mobileNo);

	Boolean existsByEmail(String email);

}
