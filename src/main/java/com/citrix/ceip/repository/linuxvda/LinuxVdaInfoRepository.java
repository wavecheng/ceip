package com.citrix.ceip.repository.linuxvda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.linuxvda.VdaInfo;

@Repository
public interface LinuxVdaInfoRepository extends JpaRepository<VdaInfo, String>{

}
