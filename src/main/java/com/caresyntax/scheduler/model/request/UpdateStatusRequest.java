package com.caresyntax.scheduler.model.request;

import com.caresyntax.scheduler.model.dataModel.Enum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateStatusRequest {


    @JsonProperty
    private Long statusId;

    @JsonProperty
    private StatusEnum status;

    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(Long statusId, StatusEnum status) {
        this.statusId = statusId;
        this.status = status;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
