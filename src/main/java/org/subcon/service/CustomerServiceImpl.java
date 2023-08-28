package org.subcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subcon.external.service.DatabaseService;
import org.subcon.model.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final DatabaseService service;

    @Autowired
    public CustomerServiceImpl(DatabaseService databaseService) {
        this.service = databaseService;
    }
    @Override
    public Customer recordCustomer(org.subcon.dto.Customer customer) {
        return this.service.recordCustomer(customer);
    }

    @Override
    public List<Customer> listCustomer() {
        return this.service.getCustomer();
    }

    @Override
    public Customer updateCustomer(org.subcon.dto.Customer customer) {
        return this.service.updateCustomer(customer);
    }

    @Override
    public String deleteCustomer(String id) {
        try {
            this.service.deleteCustomer(id);
        } catch (Exception e) {
            return "500";
        }
        return "200";
    }

}
