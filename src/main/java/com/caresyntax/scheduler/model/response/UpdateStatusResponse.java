package com.caresyntax.scheduler.model.response;

public class UpdateStatusResponse {

    private Enum status;

    private Long statusId;

    private String message;

    public UpdateStatusResponse() {
    }

    public UpdateStatusResponse(Enum status, Long statusId, String message) {
        this.status = status;
        this.statusId = statusId;
        this.message = message;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
