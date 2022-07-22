package com.dps_admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dps_admin.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

	boolean existsByMobileNo(String mobileNo);

	boolean existsByEmail(String email);

	Admin findByEmail(String email);

	Optional<Admin> findByPassword(String password);

	Optional<Admin> findByUsername(String email);

	Admin findByOtp(String otp);

	
}
