package com.caresyntax.scheduler.service;

import com.caresyntax.scheduler.model.dataModel.Patient;
import com.caresyntax.scheduler.model.request.AddPatientRequest;
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

    @Test
    public void testScheduleProcedure(){
        
    }



}
