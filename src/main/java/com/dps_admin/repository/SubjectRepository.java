package com.dps_admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dps_admin.model.Subjects;
@Repository
public interface SubjectRepository extends JpaRepository<Subjects, Long> {

	Subjects findBySubjectName(String subjectName);

}
