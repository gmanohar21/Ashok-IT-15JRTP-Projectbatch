package com.mn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mn.entity.State;
@Repository
public interface StateRepo extends JpaRepository<State, Integer> {
	public List<State> findBycountryId(Integer countryId );
}
