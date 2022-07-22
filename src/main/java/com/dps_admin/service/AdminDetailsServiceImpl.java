package com.dps_admin.service;




import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dps_admin.model.Admin;
import com.dps_admin.repository.AdminRepository;


@Service
public class AdminDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AdminRepository adminRepository;

	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	Admin admin = adminRepository.findByUsername(email).orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + email));
		return AdminDetailsImpl.buildUserWithAuth(admin);
	}

}
