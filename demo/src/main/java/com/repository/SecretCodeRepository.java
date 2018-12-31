package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.SecretCode;

@Repository
public interface SecretCodeRepository extends JpaRepository<SecretCode, Long>{

}
