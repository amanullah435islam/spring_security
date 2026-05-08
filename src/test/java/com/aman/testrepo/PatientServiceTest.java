package com.aman.testrepo;

import com.aman.enumm.Role;
import com.aman.model.Patient;
import com.aman.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void testSavePatient() {

        Patient p = new Patient();
        p.setPatientName("Rakib");
        p.setAge(25);
        p.setGender("Male");
        p.setPhone("01700000000");
        p.setPatientCode(200);
        p.setRole(Role.PATIENT);
        p.setLastVisit(new Date());

        Patient saved = patientService.createPatient(p);

        System.out.println("Saved ID: " + saved.getId());
        System.out.println("Saved Name: " + saved.getPatientName());
    }

    @Test
    public void testGetAll() {
        List<Patient> list = patientService.getAllPatient();
        System.out.println("Total: " + list.size());
    }

//    @Test
//    public void testGetById() {
//        Patient p = patientService.getPatientById(1L);
//        System.out.println(p.getPatientName());
//    }

    @Test
    public void testUpdate() {
        Patient p = new Patient();
        p.setPatientName("Updated Name");
        p.setAge(30);
        p.setGender("Male");
        p.setPhone("01700000000");
        p.setPatientCode(100);
        p.setLastVisit(new Date());

        Patient updated = patientService.updatePatient(1L, p);
        System.out.println("Updated: " + updated.getPatientName());
    }

    @Test
    public void testDelete() {
        patientService.deletePatient(1L);
        System.out.println("Deleted Successfully");
    }
}