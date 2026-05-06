package com.aman.impservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.model.Patient;
import com.aman.repository.IPatientRepo;
import com.aman.service.PatientService;

@Service
public class PatientServiceImp implements PatientService{
	
	@Autowired
	private IPatientRepo iPatientRepo;

	@Override
	public Patient createPatient(Patient p) {
		
		return iPatientRepo.save(p);
	}

	@Override
	public List<Patient> getAllPatient() {
		
		return iPatientRepo.findAll();
	}

	@Override
	public Patient updatePatient(Long id, Patient newData) {

	    Patient existing = iPatientRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Patient not found"));

	    existing.setPatientName(newData.getPatientName());
	    existing.setAge(newData.getAge());
	    existing.setGender(newData.getGender());
	    existing.setPhone(newData.getPhone());
	    existing.setLastVisit(newData.getLastVisit());

	    return iPatientRepo.save(existing);
	}
	
	@Override
	public void deletePatient(Long id) {
		
		iPatientRepo.deleteById(id);
		
//		if (!iPatientRepo.existsById(id)) {
//		    throw new RuntimeException("Patient not found");
//		}
		
	}

}
