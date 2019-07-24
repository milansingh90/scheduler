package com.caresyntax.scheduler.model.response;

public class AddPatientResponse {

    private Long patientId;
    private String message;

    public AddPatientResponse() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
