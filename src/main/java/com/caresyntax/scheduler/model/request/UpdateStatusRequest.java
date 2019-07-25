package com.caresyntax.scheduler.model.request;

import com.caresyntax.scheduler.model.dataModel.Enum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateStatusRequest {


    @JsonProperty
    @NotNull
    @NotEmpty
    private Long statusId;

    @JsonProperty
    @NotNull
    @NotEmpty
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
