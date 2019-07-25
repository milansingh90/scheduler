package com.caresyntax.scheduler.service;

import com.caresyntax.scheduler.exception.NonscheduledProcedureException;
import com.caresyntax.scheduler.exception.NotFoundException;
import com.caresyntax.scheduler.model.dataModel.Doctor;
import com.caresyntax.scheduler.model.dataModel.Patient;
import com.caresyntax.scheduler.model.dataModel.Room;
import com.caresyntax.scheduler.model.dataModel.Study;
import com.caresyntax.scheduler.model.dataModel.Enum.StatusEnum;
import com.caresyntax.scheduler.model.request.AddPatientRequest;
import com.caresyntax.scheduler.model.request.ScheduleProcedureRequest;
import com.caresyntax.scheduler.model.request.UpdateStatusRequest;
import com.caresyntax.scheduler.model.response.AddPatientResponse;
import com.caresyntax.scheduler.repo.DoctorRepository;
import com.caresyntax.scheduler.repo.PatientRepository;
import com.caresyntax.scheduler.repo.RoomRepository;
import com.caresyntax.scheduler.repo.StudyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SchedulerServiceTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    StudyRepository studyRepository;

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    Patient patient;

    @Mock
    AddPatientRequest addPatientRequest;

    @Mock
    ScheduleProcedureRequest scheduleProcedureRequest;

    @Mock
    UpdateStatusRequest updateStatusRequest;

    @Mock
    Patient patientObject;

    @Mock
    List<Doctor> doctorList;

    @Mock
    List<Room> roomList;

    @Mock
    List<Study> studyList;

    @Mock
    Room room;

    @Mock
    Doctor doctor;

    @Mock
    Study study;

    SchedulerServiceImpl schedulerService=new SchedulerServiceImpl();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        schedulerService.setDoctorRepository(doctorRepository);
        schedulerService.setPatientRepository(patientRepository);
        schedulerService.setRoomRepository(roomRepository);
        schedulerService.setStudyRepository(studyRepository);
    }

    @Test
    public void  testAddPatient()
    {
        Date date = null;
        try {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            date = simpleDateFormat.parse("2018-09-09");
        } catch(Exception e) {
            //Do Nothing
        }

        Patient patientObject = new Patient();
        patientObject.setId(1);

        BDDMockito.given(addPatientRequest.getName()).willReturn("testName");
        BDDMockito.given(addPatientRequest.getGender()).willReturn("Male");
        BDDMockito.given(addPatientRequest.getDayOfBirth()).willReturn(date);

        BDDMockito.given(patientRepository.save(Mockito.any(Patient.class))).willReturn(patientObject);


        AddPatientResponse response=schedulerService.addPatient(addPatientRequest);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientRepository,Mockito.times(1)).save(patientArgumentCaptor.capture());
        Mockito.verifyNoMoreInteractions(patientRepository);
        Assert.assertEquals(Long.valueOf(1), response.getPatientId());
    }

    @Test(expected=NonscheduledProcedureException.class)
    public void testScheduleProcedureWithNoDoctorFoundException(){
        BDDMockito.given(scheduleProcedureRequest.getPatientId()).willReturn(12l);
        BDDMockito.given(patientRepository.findById(12l)).willReturn(Optional.of(patientObject));
        BDDMockito.given(doctorRepository.findByOccupancyStatus()).willReturn(doctorList);
        BDDMockito.given(doctorList.size()).willReturn(0);
        schedulerService.scheduleProcedure(scheduleProcedureRequest);

    }

    @Test(expected=NonscheduledProcedureException.class)
    public void testScheduleProcedureWithNoRoomFoundException(){
        BDDMockito.given(scheduleProcedureRequest.getPatientId()).willReturn(12l);
        BDDMockito.given(patientRepository.findById(12l)).willReturn(Optional.of(patientObject));
        BDDMockito.given(doctorRepository.findByOccupancyStatus()).willReturn(doctorList);
        BDDMockito.given(doctorList.size()).willReturn(1);
        BDDMockito.given(roomRepository.findByOccupancyStatus()).willReturn(roomList);
        BDDMockito.given(roomList.size()).willReturn(0);
        schedulerService.scheduleProcedure(scheduleProcedureRequest);

    }

    @Test
    public void testScheduleProcedureWithNoRoom(){
        BDDMockito.given(scheduleProcedureRequest.getPatientId()).willReturn(12l);
        BDDMockito.given(patientRepository.findById(12l)).willReturn(Optional.of(patientObject));
        BDDMockito.given(doctorRepository.findByOccupancyStatus()).willReturn(doctorList);
        BDDMockito.given(doctorList.size()).willReturn(1);
        BDDMockito.given(doctorList.get(0)).willReturn(doctor);
        BDDMockito.given(roomRepository.findByOccupancyStatus()).willReturn(roomList);

        BDDMockito.given(roomList.size()).willReturn(1);
        BDDMockito.given(roomList.get(0)).willReturn(room);
        Study studyResponse = new Study();
        studyResponse.setId(1l);
        studyResponse.setDoctor(doctor);
        studyResponse.setRoom(room);
        BDDMockito.given(doctor.getName()).willReturn("Dr.Alex");
        BDDMockito.given(room.getName()).willReturn("ward1");
        BDDMockito.given(studyRepository.save(Mockito.any(Study.class))).willReturn(studyResponse);
        schedulerService.scheduleProcedure(scheduleProcedureRequest);
        ArgumentCaptor<Study> patientArgumentCaptor = ArgumentCaptor.forClass(Study.class);
        Mockito.verify(studyRepository,Mockito.times(1)).save(patientArgumentCaptor.capture());
        Mockito.verifyNoMoreInteractions(studyRepository);
        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        Mockito.verify(roomRepository,Mockito.times(1)).save(roomArgumentCaptor.capture());
        ArgumentCaptor<Doctor> doctorArgumentCaptor = ArgumentCaptor.forClass(Doctor.class);
        Mockito.verify(doctorRepository,Mockito.times(1)).save(doctorArgumentCaptor.capture());
        Mockito.verify(room,Mockito.times(1)).setOccupancyStatus("UNAVAILABLE");
        Mockito.verify(doctor,Mockito.times(1)).setOccupancyStatus("UNAVAILABLE");

    }

    @Test(expected=NotFoundException.class)
    public void testUpdateProcedureStatus()
    {
        BDDMockito.given(updateStatusRequest.getStatusId()).willReturn(12l);
        BDDMockito.given(studyRepository.findByStatusId(updateStatusRequest.getStatusId())).willReturn(studyList);
        BDDMockito.given(studyList.size()).willReturn(0);
        schedulerService.updateProcedureStatus(updateStatusRequest);
    }

    @Test
    public void testUpdateProcedureStatusNonException()
    {
        BDDMockito.given(updateStatusRequest.getStatusId()).willReturn(12l);
        BDDMockito.given(studyRepository.findByStatusId(updateStatusRequest.getStatusId())).willReturn(studyList);
        BDDMockito.given(studyList.size()).willReturn(1);
        BDDMockito.given(studyList.get(0)).willReturn(study);
        BDDMockito.given(study.getRoom()).willReturn(room);
        BDDMockito.given(study.getDoctor()).willReturn(doctor);
        Study studyResponse = new Study();
        studyResponse.setId(1l);
        studyResponse.setStatus(StatusEnum.FINISHED);
        BDDMockito.given(studyRepository.save(Mockito.any(Study.class))).willReturn(studyResponse);
        schedulerService.updateProcedureStatus(updateStatusRequest);
        ArgumentCaptor<Study> doctorArgumentCaptor = ArgumentCaptor.forClass(Study.class);
        Mockito.verify(studyRepository,Mockito.times(1)).save(doctorArgumentCaptor.capture());
        ArgumentCaptor<Room> roomArgumentCaptor2 = ArgumentCaptor.forClass(Room.class);
        Mockito.verify(roomRepository,Mockito.times(1)).save(roomArgumentCaptor2.capture());
        ArgumentCaptor<Doctor> doctorArgumentCaptor2 = ArgumentCaptor.forClass(Doctor.class);
        Mockito.verify(doctorRepository,Mockito.times(1)).save(doctorArgumentCaptor2.capture());
        Mockito.verify(room,Mockito.times(1)).setOccupancyStatus("AVAILABLE");
        Mockito.verify(doctor,Mockito.times(1)).setOccupancyStatus("AVAILABLE");
    }

    @Test
    public void testUpdateProcedureStatusNonExceptionUnFinished()
    {
        BDDMockito.given(updateStatusRequest.getStatusId()).willReturn(12l);
        BDDMockito.given(studyRepository.findByStatusId(updateStatusRequest.getStatusId())).willReturn(studyList);
        BDDMockito.given(studyList.size()).willReturn(1);
        BDDMockito.given(studyList.get(0)).willReturn(study);
        Study studyResponse = new Study();
        studyResponse.setId(1l);
        studyResponse.setStatus(StatusEnum.IN_PROGRESS);
        BDDMockito.given(studyRepository.save(Mockito.any(Study.class))).willReturn(studyResponse);
        schedulerService.updateProcedureStatus(updateStatusRequest);
        ArgumentCaptor<Study> doctorArgumentCaptor = ArgumentCaptor.forClass(Study.class);
        Mockito.verify(studyRepository,Mockito.times(1)).save(doctorArgumentCaptor.capture());
        ArgumentCaptor<Room> roomArgumentCaptor2 = ArgumentCaptor.forClass(Room.class);
        Mockito.verify(roomRepository,Mockito.never()).save(roomArgumentCaptor2.capture());
        ArgumentCaptor<Doctor> doctorArgumentCaptor2 = ArgumentCaptor.forClass(Doctor.class);
        Mockito.verify(doctorRepository,Mockito.never()).save(doctorArgumentCaptor2.capture());

    }



}