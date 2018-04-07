package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, String>{

}
