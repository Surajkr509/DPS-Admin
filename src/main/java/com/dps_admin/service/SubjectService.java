package com.dps_admin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps_admin.model.Subjects;
import com.dps_admin.repository.SubjectRepository;
import com.dps_admin.utils.Constants;

@Service
public class SubjectService {
	
	@Autowired
	SubjectRepository subjectRepository;
	
	public void saveSubject(Subjects subjects) {
		subjects.setCreatedAt(Constants.getDateAndTime());
		subjects.setUpdatedAt(Constants.getDateAndTime());
		subjects.setStatus(true);
		subjectRepository.save(subjects);
	}

	public Subjects getById(Long id) {
		Optional<Subjects> subjectDetails = subjectRepository.findById(id);
		return subjectDetails.isPresent()?subjectDetails.get():null;
	}

	public String updateSubject(Subjects subjects) {
		Optional<Subjects> subjectData = subjectRepository.findById(subjects.getId());
		Subjects updateSub =  subjectData.get();
		updateSub.setSubjectName(subjects.getSubjectName());
		updateSub.setUpdatedAt(Constants.getDateAndTime());
		subjectRepository.save(updateSub);
		return null;
	}
	
	


	

}
