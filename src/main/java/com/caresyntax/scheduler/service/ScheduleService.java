package com.caresyntax.scheduler.service;


import com.caresyntax.scheduler.model.request.AddPatientRequest;
import com.caresyntax.scheduler.model.request.ScheduleProcedureRequest;
import com.caresyntax.scheduler.model.request.UpdateStatusRequest;
import com.caresyntax.scheduler.model.response.AddPatientResponse;
import com.caresyntax.scheduler.model.response.ScheduleProcedureResponse;
import com.caresyntax.scheduler.model.response.UpdateStatusResponse;

public interface ScheduleService {

    AddPatientResponse addPatient(AddPatientRequest patient);

    ScheduleProcedureResponse scheduleProcedure(ScheduleProcedureRequest scheduleProcedureRequest);

    UpdateStatusResponse updateProcedureStatus(UpdateStatusRequest updateStatusRequest);
}
