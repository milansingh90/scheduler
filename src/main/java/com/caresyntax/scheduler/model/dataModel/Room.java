package com.caresyntax.scheduler.model.dataModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Room {

    @NotNull
    @Id
    @Column
    private Long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column(name="occupancy_status")
    private String occupancyStatus;

    public Room() {
    }

    public Room(Long id, String name,  String occupancyStatus) {
        this.id = id;
        this.name = name;
        this.occupancyStatus = occupancyStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOccupancyStatus() {
        return occupancyStatus;
    }

    public void setOccupancyStatus(String occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
