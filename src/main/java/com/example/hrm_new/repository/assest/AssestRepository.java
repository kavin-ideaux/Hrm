package com.example.hrm_new.repository.assest;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hrm_new.entity.Assest.Assest;

public interface AssestRepository extends JpaRepository<Assest, Long>{
	
	@Query(value = " select a.*,b.brand_name,kb.keyboard_brand_name,mb.mouse_brand_name "
			+ " from assest as a"
			+ " join brand as b on b.brand_id=a.brand_id "
			+ " join keyboard_brand as kb on kb.keyboard_brand_id=a.keyboard_brand_id "
			+ "  join mouse_brand as mb on mb.mouse_brand_id=a.mouse_brand_id "
			, nativeQuery = true)
	List<Map<String, Object>> allAsesstDetails();

	List<Assest> findByStatusTrue();

	
	
	

}
