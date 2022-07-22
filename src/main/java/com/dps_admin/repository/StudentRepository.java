package com.dps_admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dps_admin.model.Student;



public interface StudentRepository extends JpaRepository<Student, Long> {
	
	boolean existsByMobileNo(String mobileNo);
	Boolean existsByEmail(String email);
	Optional<Student> findByEmail(String email);
	Student findByUsername(String username);
	Optional<Student> findByPassword(String password);
	Optional<Student> findByName(String name);
	Optional<Student> findByMobileNo(String mobileNo);
	Student findByOtp(String otp);

}
