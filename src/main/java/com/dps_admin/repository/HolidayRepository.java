package com.dps_admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dps_admin.model.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Long> {

	Holiday findByFestivals(String festivals);

}
