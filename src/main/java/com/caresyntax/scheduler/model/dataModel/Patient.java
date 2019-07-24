package com.caresyntax.scheduler.model.dataModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    @Size(max = 65)
    private String name;

    @Column
    @Size(max = 6)
    private String gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    private Date dayOfBirth;

    public Patient() {
    }

    public Patient(long id, String name, String gender, Date dayOfBirth) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;

    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

}
