package com.citrix.ceip.repository.vdaclearnup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.vdacleanup.Metadata;

@Repository
public interface VdaCleanupMetadataRepository extends JpaRepository<Metadata, Integer> {

}
