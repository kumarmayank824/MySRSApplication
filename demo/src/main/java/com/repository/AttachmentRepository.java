package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>{
    
	@Query(value ="SELECT * FROM attachment LIMIT ?2 OFFSET ?1", nativeQuery = true)
	List<Attachment> findAll(Integer offset,Integer itemsPerPage);
    
	@Query(value="select * FROM ATTACHMENT a where a.semester=?1 and a.batch=?2 and a.course=?3", nativeQuery = true)
    List<Attachment> findAttachmentForTeacher(String semester,String batch, String course);
}
