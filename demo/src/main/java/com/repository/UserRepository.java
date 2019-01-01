package com.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByConfirmationToken(Object object);
	
    @Modifying
    @Query(value="UPDATE USERS SET username=?1, semester=?2, batch=?3, course=?4 WHERE user_id=?5 ", nativeQuery = true)
    void updateUserDetail(String username, String semester, String batch, String course, int userId);
    
    @Query(value="SELECT secret_code from secret_code", nativeQuery = true)
    String getSecretCode();
	
    @Modifying
    @Query(value="UPDATE USERS SET enabled=?1 WHERE email=?2 ", nativeQuery = true)
    void disableUserAccount(int flag, String email);
    
    @Query(value="SELECT  GROUP_CONCAT(DISTINCT email SEPARATOR ',') AS emailLst FROM users WHERE sign_in_type = 'Coordinator'", nativeQuery = true)
    String getCoordinatorEmailLst();
}
