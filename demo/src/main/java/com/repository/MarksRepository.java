package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.Marks;


public interface MarksRepository extends JpaRepository<Marks, Long>{
	  
	@Query(value="select * from Marks a where a.attachment_id=?1", nativeQuery = true)
    public Marks getMarksByAttachmentId(Long attachmentId);
}
