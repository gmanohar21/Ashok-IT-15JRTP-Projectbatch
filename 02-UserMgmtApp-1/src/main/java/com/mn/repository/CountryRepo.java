package com.mn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mn.entity.Country;
@Repository
public interface CountryRepo extends JpaRepository<Country,Integer> {

}
