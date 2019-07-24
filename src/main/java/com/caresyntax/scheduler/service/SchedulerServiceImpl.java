package com.caresyntax.scheduler.service;

import com.caresyntax.scheduler.exception.GeneralException;
import com.caresyntax.scheduler.exception.NonscheduledProcedureException;
import com.caresyntax.scheduler.exception.NotFoundException;
import com.caresyntax.scheduler.model.dataModel.Doctor;
import com.caresyntax.scheduler.model.dataModel.Enum.StatusEnum;
import com.caresyntax.scheduler.model.dataModel.Patient;
import com.caresyntax.scheduler.model.dataModel.Room;
import com.caresyntax.scheduler.model.dataModel.Study;
import com.caresyntax.scheduler.model.request.AddPatientRequest;
import com.caresyntax.scheduler.model.request.ScheduleProcedureRequest;
import com.caresyntax.scheduler.model.request.UpdateStatusRequest;
import com.caresyntax.scheduler.model.response.AddPatientResponse;
import com.caresyntax.scheduler.model.response.ScheduleProcedureResponse;
import com.caresyntax.scheduler.model.response.UpdateStatusResponse;
import com.caresyntax.scheduler.repo.DoctorRepository;
import com.caresyntax.scheduler.repo.PatientRepository;
import com.caresyntax.scheduler.repo.RoomRepository;
import com.caresyntax.scheduler.repo.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulerServiceImpl implements ScheduleService{

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    RoomRepository roomRepository;

    public static final String UPDATE_STATUS_MESSAGE = "Procedure Status updated successfully";
    public static final String ADD_PATIENT_SUCCESS_MESSAGE = "AddPatientRequest details saved successfully";

    @Override
    public AddPatientResponse addPatient(AddPatientRequest addPatientRequest) {

        Patient patient = new Patient();
        patient.setName(addPatientRequest.getName());
        patient.setDayOfBirth(addPatientRequest.getDayOfBirth());
        patient.setGender(addPatientRequest.getGender());
        Patient response = patientRepository.save(patient);

        AddPatientResponse savePatientResponse = new AddPatientResponse();
        savePatientResponse.setPatientId(response.getId());
        savePatientResponse.setMessage(ADD_PATIENT_SUCCESS_MESSAGE);
        return savePatientResponse;
    }

    @Override
    public ScheduleProcedureResponse scheduleProcedure(ScheduleProcedureRequest scheduleProcedureRequest) {
        Study study = new Study();

        ScheduleProcedureResponse scheduleProcedureResponse = new ScheduleProcedureResponse();

        Optional<Patient> patientObject = patientRepository.findById(scheduleProcedureRequest.getPatientId());
        if(patientObject.isPresent()){
            study.setPatient(patientObject.get());
        } else {
           throw new NotFoundException("AddPatientRequest record unavailable for the given patient id.");
        }

        List<Doctor> doctorList = doctorRepository.findByOccupancyStatus();
        List<Room> roomList = roomRepository.findByOccupancyStatus();

        if(doctorList.size()>0) {
            study.setDoctor(doctorList.get(0));
        } else {
            throw new NonscheduledProcedureException("No Doctor available.");
        }

        if(roomList.size()>0) {
            study.setRoom(roomList.get(0));
        } else {
            throw new NonscheduledProcedureException("No Room available.");
        }

        study.setDescription(scheduleProcedureRequest.getDescription());
        study.setPlannedStartTime(scheduleProcedureRequest.getPlannedStartTime());
        study.setStatus(scheduleProcedureRequest.getStatus());
        study.setEstimatedEndTime(scheduleProcedureRequest.getEstimatedEndTime());

        Study studyResponse = studyRepository.save(study);

        Room room = roomList.get(0);
        Doctor doctor = doctorList.get(0);
        room.setOccupancyStatus("UNAVAILABLE");
        doctor.setOccupancyStatus("UNAVAILABLE");

        roomRepository.save(room);
        doctorRepository.save(doctor);

        scheduleProcedureResponse.setStatusId(studyResponse.getId());
        scheduleProcedureResponse.setStatus(studyResponse.getStatus());
        scheduleProcedureResponse.setDoctorName(studyResponse.getDoctor().getName());
        scheduleProcedureResponse.setRoom(studyResponse.getRoom().getName());

        return scheduleProcedureResponse;

    }

    @Override
    public UpdateStatusResponse updateProcedureStatus(UpdateStatusRequest updateStatusRequest) {
        Study studyResponse = new Study();
        UpdateStatusResponse updateStatusResponse = new UpdateStatusResponse();

        List<Study> studyList = studyRepository.findByStatusId(updateStatusRequest.getStatusId());
        if(studyList.size()>0) {
            //if(studyList.size()==1) {
                Study study = studyList.get(0);
                study.setStatus(updateStatusRequest.getStatus());
                studyResponse = studyRepository.save(study);
           // } else {
           //     throw new GeneralException("More than one response based on study Id. Please check study Id.");
           // }
        } else {
            throw new NotFoundException("Study record not available for the given study id");
        }

        if(studyResponse.getStatus()== StatusEnum.FINISHED){
            Room room = studyList.get(0).getRoom();
            room.setOccupancyStatus("AVAILABLE"); // create a constant for available and unavailable
            roomRepository.save(room);

            Doctor doctor = studyList.get(0).getDoctor();
            doctor.setOccupancyStatus("AVAILABLE");
            doctorRepository.save(doctor);
        }

        updateStatusResponse.setStatusId(studyResponse.getId());
        updateStatusResponse.setStatus(studyResponse.getStatus());
        updateStatusResponse.setMessage(UPDATE_STATUS_MESSAGE);

        return updateStatusResponse;


    }

    public PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public StudyRepository getStudyRepository() {
        return studyRepository;
    }

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public DoctorRepository getDoctorRepository() {
        return doctorRepository;
    }

    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
}


