package com.caresyntax.scheduler.repo;

import com.caresyntax.scheduler.model.dataModel.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long>{

    @Query("SELECT r FROM Room r WHERE r.occupancyStatus='AVAILABLE'")
    List<Room> findByOccupancyStatus();
}
