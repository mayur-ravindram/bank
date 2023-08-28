package org.subcon.service;

import org.subcon.dto.Customer;

import java.util.List;

public interface CustomerService {
    org.subcon.model.Customer recordCustomer(Customer customer);

    List<org.subcon.model.Customer> listCustomer();

    org.subcon.model.Customer updateCustomer(Customer customer);

    String deleteCustomer(String id);
}
