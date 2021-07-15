package com.mn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mn.entity.City;
@Repository
public interface CityRepo extends JpaRepository<City, Integer> {
	public List<City> findBystateId(Integer stateId);
}
