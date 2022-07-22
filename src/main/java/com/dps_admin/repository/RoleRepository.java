package com.dps_admin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dps_admin.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRoleName(String role);

	@Query("SELECT r FROM Role r WHERE r.roleName != 'ADMIN'")
	List<Role> findAllRole();

}
