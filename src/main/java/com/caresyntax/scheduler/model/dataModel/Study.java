package com.caresyntax.scheduler.model.dataModel;



import com.caresyntax.scheduler.model.dataModel.Enum.StatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;


    @NotNull
    @Column

    private String description;

    @NotNull
    @Column
    private StatusEnum status;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column
    private Date plannedStartTime;

    @Temporal(TemporalType.DATE)
    @Column
    private Date estimatedEndTime;


    public Study() {
    }

    public Study(Long id, @NotNull Patient patient, @NotNull Doctor doctor,
                 @NotNull Room room, @NotNull String description, @NotNull StatusEnum status,
                 @NotNull Date plannedStartTime, Date estimatedEndTime) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.room = room;
        this.description = description;
        this.status = status;
        this.plannedStartTime = plannedStartTime;
        this.estimatedEndTime = estimatedEndTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getPlannedStartTime() {
        return plannedStartTime;
    }

    public void setPlannedStartTime(Date plannedStartTime) {
        this.plannedStartTime = plannedStartTime;
    }

    public Date getEstimatedEndTime() {
        return estimatedEndTime;
    }

    public void setEstimatedEndTime(Date estimatedEndTime) {
        this.estimatedEndTime = estimatedEndTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
