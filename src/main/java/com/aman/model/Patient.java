package com.aman.model;

import java.util.Date;

import com.aman.enumm.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name="patient_code", unique = true)
	private int patientCode;
	
	@Column(nullable = false, name="patient_name")
	private String patientName;
	
	@Column(nullable = false, name="age")
	private int age;
	
	@Column(nullable = false, name="gender")
	private String gender;
	
	@Column(nullable = false, name="phone")
	private String phone;
	
	@Column(nullable = false, name="last_visit")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lastVisit;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", length = 30)
	private Role role;
}
