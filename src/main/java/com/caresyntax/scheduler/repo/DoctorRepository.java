package com.caresyntax.scheduler.repo;

import com.caresyntax.scheduler.model.dataModel.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{


    @Query("select d from Doctor d where d.occupancyStatus='AVAILABLE'")
    List<Doctor> findByOccupancyStatus();
}
