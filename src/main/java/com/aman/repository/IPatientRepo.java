package com.aman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.model.Patient;

@Repository
public interface IPatientRepo extends JpaRepository<Patient, Long>{

}
