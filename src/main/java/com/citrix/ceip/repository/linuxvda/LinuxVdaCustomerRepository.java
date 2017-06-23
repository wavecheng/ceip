package com.citrix.ceip.repository.linuxvda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.linuxvda.Customer;

@Repository
public interface LinuxVdaCustomerRepository extends JpaRepository<Customer, Integer>{

}
