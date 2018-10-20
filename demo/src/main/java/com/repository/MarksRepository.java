package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.Marks;


public interface MarksRepository extends JpaRepository<Marks, Long>{
	  
	@Query(value="select * from Marks a where a.attachment_id=?1", nativeQuery = true)
    public Marks getMarksByAttachmentId(Long attachmentId);
    
	@Query(value="select * from Marks a where a.email=?1", nativeQuery = true)
	public List<Marks> findMarkedAttachmentByEmailId(String email);
}
