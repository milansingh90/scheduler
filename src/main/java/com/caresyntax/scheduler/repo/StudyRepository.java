package com.caresyntax.scheduler.repo;

import com.caresyntax.scheduler.model.dataModel.Patient;
import com.caresyntax.scheduler.model.dataModel.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Study save(Study study);

    @Query("SELECT s FROM Study s WHERE s.id=?1")
    List<Study> findByStatusId(Long id);
}
