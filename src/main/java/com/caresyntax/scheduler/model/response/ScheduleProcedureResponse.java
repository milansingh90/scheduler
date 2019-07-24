package com.caresyntax.scheduler.model.response;

public class ScheduleProcedureResponse {

    private Long statusId;

    private Enum status;

    private String doctorName;

    private String room;

    public ScheduleProcedureResponse() {
    }

    public ScheduleProcedureResponse(Long statusId, Enum status, String doctorName, String room) {
        this.statusId = statusId;
        this.status = status;
        this.doctorName = doctorName;
        this.room = room;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
