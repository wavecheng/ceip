package com.citrix.ceip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.LastUpdateTime;

@Repository
public interface LastUpdateTimeRepository extends JpaRepository<LastUpdateTime, String> {

}
