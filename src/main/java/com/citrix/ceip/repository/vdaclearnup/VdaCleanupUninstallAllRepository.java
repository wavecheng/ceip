package com.citrix.ceip.repository.vdaclearnup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.vdacleanup.UninstallAll;

@Repository
public interface VdaCleanupUninstallAllRepository extends JpaRepository<UninstallAll, Integer> {

}
