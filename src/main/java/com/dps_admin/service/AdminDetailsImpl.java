package com.dps_admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dps_admin.model.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdminDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private String mobileNo;
	private boolean loginStatus;

	public AdminDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities, String mobileNo, boolean loginStatus) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.mobileNo = mobileNo;
		this.loginStatus = loginStatus;
	}

	public static AdminDetailsImpl buildUserWithAuth(Admin user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRoleId().getRoleName()));
		return new AdminDetailsImpl(user.getId(),user.getUsername(), user.getEmail(),
				user.getPassword(), authorities, user.getMobileNo(), user.isLoginStatus());
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AdminDetailsImpl user = (AdminDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
