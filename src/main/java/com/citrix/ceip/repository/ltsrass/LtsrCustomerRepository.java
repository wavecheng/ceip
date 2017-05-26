package com.citrix.ceip.repository.ltsrass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citrix.ceip.model.ltsrass.Customer;

@Repository
public interface LtsrCustomerRepository extends JpaRepository<Customer, String> {

}
