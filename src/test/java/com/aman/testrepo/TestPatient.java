package com.aman.testrepo;

import com.aman.enumm.Role;
import com.aman.model.Patient;
import com.aman.repository.IPatientRepo;
import com.aman.springsecurity.SpringsecurityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringsecurityApplication.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestPatient {

    @Autowired
    private IPatientRepo patientRepo;

    @Test
    public void save() {

        Patient p = new Patient();

        p.setPatientCode(1001);
        p.setPatientName("Aman Test");
        p.setAge(25);
        p.setGender("Male");
        p.setPhone("01700000000");
        p.setLastVisit(new Date());
        p.setRole(Role.PATIENT);

        Patient saved = patientRepo.save(p);

        System.out.println("Saved Successfully");


        Optional<Patient> found =
                patientRepo.findById(saved.getId());

        assertThat(found).isPresent();

        assertThat(found.get().getPatientName())
                .isEqualTo("Aman Test");
    }









    // Test Get All
    @Test
    public void testFindAll() {
        List<Patient> list = patientRepo.findAll();
        System.out.println("Total Patients: " + list.size());
    }
// Test Get By ID
    @Test
    public void testFindById() {
        Patient p = patientRepo.findById(1L).orElse(null);
        System.out.println(p);
    }
// Test Exists
    @Test
    public void testExists() {
        boolean exists = patientRepo.existsById(1L);
        System.out.println("Exists: " + exists);
    }
// Test Delete
    @Test
    public void testDelete() {
        patientRepo.deleteById(1L);
        System.out.println("Deleted Successfully");
    }

}
