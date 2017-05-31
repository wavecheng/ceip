package com.citrix.ceip.repository.upm;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citrix.ceip.model.upm.Customer;


public interface UpmCustomerRepository extends JpaRepository<Customer, String> {

}
