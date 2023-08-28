package org.subcon.external.service;

import org.subcon.model.Customer;

import java.util.List;

public interface DatabaseService {

    Customer recordCustomer(org.subcon.dto.Customer customer);

    List<org.subcon.model.Customer> getCustomer();

    Customer updateCustomer(org.subcon.dto.Customer customer);

    String deleteCustomer(String customerId);
}
