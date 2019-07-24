package com.caresyntax.scheduler.controller;

import com.caresyntax.scheduler.model.dataModel.Patient;
import com.caresyntax.scheduler.model.request.AddPatientRequest;
import com.caresyntax.scheduler.model.request.ScheduleProcedureRequest;
import com.caresyntax.scheduler.model.request.UpdateStatusRequest;
import com.caresyntax.scheduler.model.response.AddPatientResponse;
import com.caresyntax.scheduler.model.response.ScheduleProcedureResponse;
import com.caresyntax.scheduler.model.response.UpdateStatusResponse;
import com.caresyntax.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/addpatient")
    public ResponseEntity<AddPatientResponse> addPatient(@RequestBody AddPatientRequest patient){

        // this

        AddPatientResponse response = scheduleService.addPatient(patient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/scheduleprocedure")
    public ResponseEntity<ScheduleProcedureResponse> scheduleProcedure(
            @RequestBody ScheduleProcedureRequest scheduleProcedureRequest){

        ScheduleProcedureResponse response = scheduleService.scheduleProcedure(scheduleProcedureRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/updatestatus")
    public ResponseEntity<UpdateStatusResponse> updateProcedureStatus(
            @RequestBody UpdateStatusRequest updateStatusRequest) {

        UpdateStatusResponse response = scheduleService.updateProcedureStatus(updateStatusRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
