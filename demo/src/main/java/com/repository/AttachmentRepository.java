package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>{

}
