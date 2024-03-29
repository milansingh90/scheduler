package com.caresyntax.scheduler.model.request;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.DeserializationConfig;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;


public class AddPatientRequest {

    @JsonProperty(value = "name", required = true)
    @NotNull
    @NotEmpty
    private String name;

    @JsonProperty
    private String gender;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dayOfBirth;

    public AddPatientRequest() {
    }


    public AddPatientRequest(String name, String gender, Date dayOfBirth) {
        this.name = name;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;

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
