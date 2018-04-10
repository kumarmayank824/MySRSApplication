package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.domain.User;

@Repository
public interface RegistrationRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByConfirmationToken(Object object);
}
