package com.dps_admin.service;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps_admin.model.Role;
import com.dps_admin.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepo;
	
	public void addRole(@Valid Role role) {
		role.setActive(true);
		roleRepo.save(role);
		
	}

	public void deleteRole(Long id) {
		roleRepo.deleteById(id);
	}

	
}
