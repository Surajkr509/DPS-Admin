package com.dps_admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dps_admin.model.ContentInfo;

public interface ContentRepository extends JpaRepository<ContentInfo, Long> {

}
