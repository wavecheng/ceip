package com.citrix.ceip.repository.vdaclearnup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.vdacleanup.RebootStat;

@Repository
public interface VdaCleanupRebootStatRepository extends JpaRepository<RebootStat, Integer> {

}
