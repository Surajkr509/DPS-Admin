package com.dps_admin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps_admin.model.Holiday;
import com.dps_admin.repository.HolidayRepository;
import com.dps_admin.utils.Constants;

@Service
public class HolidayService {
	
	@Autowired
	HolidayRepository holidayRepository;

	public Object saveHoliday(Holiday holiday) {
		holiday.setCreatedAt(Constants.getDateAndTime());
		holiday.setUpdatedAt(Constants.getDateAndTime());
		holiday.setActive(true);
		return 	holidayRepository.save(holiday);
	}

	public Holiday getById(Long id) {
		Optional<Holiday> holi = holidayRepository.findById(id);
		return holi.isPresent()?holi.get():null;
	}

	public String updateHoliday(Holiday holiday) {
		System.err.println(holiday.getDays());
		Optional<Holiday> holiday2=holidayRepository.findById(holiday.getId());
		Holiday holiData = holiday2.get();
		holiData.setFestivals(holiday.getFestivals());
		holiData.setDate(holiday.getDate());
		holiData.setDays(holiday.getDays());
		holiData.setUpdatedAt(Constants.getDateAndTime());
		holiData.setActive(true);
		holidayRepository.save(holiData);
		return null;
	}

}
