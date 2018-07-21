package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
  
	@Query(value="select * from Rating a where a.attachment_id=?1", nativeQuery = true)
    public List<Rating> findRatingByAttachmentId(Long attachmentId);
}

