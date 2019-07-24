package com.caresyntax.scheduler.repo;

import com.caresyntax.scheduler.model.dataModel.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
