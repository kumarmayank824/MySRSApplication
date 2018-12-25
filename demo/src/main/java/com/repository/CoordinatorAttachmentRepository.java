package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.CoordinatorAttachment;

@Repository
public interface CoordinatorAttachmentRepository extends JpaRepository<CoordinatorAttachment, Long>{
 
	@Query(value="select * FROM COORDINATOR_ATTACHMENT a where a.author_email=?1 order by a.uploaded_date desc", nativeQuery = true)
	List<CoordinatorAttachment> findAttachmentByEmailId(String email);
}
