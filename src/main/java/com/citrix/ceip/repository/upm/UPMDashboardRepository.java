package com.citrix.ceip.repository.upm;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citrix.ceip.model.upm.UPMDashboardData;

public interface UPMDashboardRepository extends JpaRepository<UPMDashboardData, Integer> {

}
