package com.caresyntax.scheduler.controller;


import com.caresyntax.scheduler.model.request.AddPatientRequest;
import com.caresyntax.scheduler.model.request.ScheduleProcedureRequest;
import com.caresyntax.scheduler.model.request.UpdateStatusRequest;
import com.caresyntax.scheduler.model.response.AddPatientResponse;
import com.caresyntax.scheduler.model.response.ScheduleProcedureResponse;
import com.caresyntax.scheduler.model.response.UpdateStatusResponse;
import com.caresyntax.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ScheduleController {

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @Autowired
    ScheduleService scheduleService;


    @GetMapping(value = "/addpatient")
    public String showAddPatientForm(Model model) {
        model.addAttribute("addpatient", new AddPatientRequest());
        return "addpatient";
    }

    @PostMapping("/addpatient")
    public String addPatient(
            @Valid @ModelAttribute("patient") AddPatientRequest patient,
            BindingResult result, ModelMap model){

        // this
        AddPatientResponse response = scheduleService.addPatient(patient);
        if(result.hasErrors()){
            return this.returnErrorView(result, model);
        }
        model.addAttribute("patientId", response.getPatientId());
        model.addAttribute("message", response.getMessage());
        return "patientView";

    }

    @GetMapping(value = "/scheduleprocedure")
    public String scheduleProcedure(Model model) {
        model.addAttribute("scheduleprocedure", new ScheduleProcedureRequest());
        return "scheduleprocedure";
    }

    @PostMapping("/scheduleprocedure")
    public String scheduleProcedure(
            @Valid @ModelAttribute ScheduleProcedureRequest scheduleProcedureRequest,
            BindingResult result, ModelMap model){

        ScheduleProcedureResponse response = scheduleService.scheduleProcedure(scheduleProcedureRequest);
        if(result.hasErrors()){
            return this.returnErrorView(result, model);
        }
        model.addAttribute("statusId", response.getStatusId());
        model.addAttribute("status", response.getStatus());
        model.addAttribute("doctorName", response.getDoctorName());
        model.addAttribute("room", response.getRoom());

        return "scheduledProcedureView";

    }

    //No UI available for the request. Implemented for only two calls. Uses Response Entity to provide a application/JSON response
    @PutMapping("/updatestatus")
    public ResponseEntity<UpdateStatusResponse> updateProcedureStatus(
            @Valid @RequestBody UpdateStatusRequest updateStatusRequest) {

        UpdateStatusResponse response = scheduleService.updateProcedureStatus(updateStatusRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private String returnErrorView(BindingResult error, ModelMap model) {
        model.addAttribute("message", error);
        return "error";
    }

}
