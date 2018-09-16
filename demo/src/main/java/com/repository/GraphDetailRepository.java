package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.GraphDetail;

public interface GraphDetailRepository extends JpaRepository<GraphDetail,Long> {

	@Query(value="select id,count(*) as count,category from attachment group by category", nativeQuery = true)
    public List<GraphDetail> getGraphDetails();
	
}





