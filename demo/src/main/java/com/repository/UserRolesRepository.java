package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.domain.UserRole;

@Repository
public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
	
	@Query("select a.role from UserRole a, User b where b.email=?1 and a.user.userId=b.userId")
    public List<String> findRoleByEmail(String email);
	
}
