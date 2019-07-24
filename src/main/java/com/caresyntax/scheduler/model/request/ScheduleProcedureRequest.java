package com.caresyntax.scheduler.model.request;

import com.caresyntax.scheduler.model.dataModel.Enum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Date;

public class ScheduleProcedureRequest {

    @JsonProperty("patientId")
    private Long patientId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("plannedStartTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CEST")
    private Date plannedStartTime;

    @JsonProperty("estimatedEndTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CEST")
    private Date estimatedEndTime;

    public ScheduleProcedureRequest() {
    }

    public ScheduleProcedureRequest(Long patientId, String description, StatusEnum status, Date plannedStartTime, Date estimatedEndTime) {
        this.patientId = patientId;
        this.description = description;
        this.status = status;
        this.plannedStartTime = plannedStartTime;
        this.estimatedEndTime = estimatedEndTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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
}
