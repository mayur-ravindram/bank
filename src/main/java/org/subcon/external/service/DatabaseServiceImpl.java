package org.subcon.external.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subcon.dto.Customer;
import org.subcon.external.proxy.DatabaseProxy;
import org.subcon.model.Account;
import org.subcon.model.AccountStatus;
import org.subcon.model.Address;
import org.subcon.model.Individual;

import java.util.List;

import static org.subcon.util.CommonUtilities.getAccountStatus;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final DatabaseProxy proxy;

    @Autowired
    public DatabaseServiceImpl(DatabaseProxy databaseProxy) {
        this.proxy = databaseProxy;
    }

    @Override
    public org.subcon.model.Customer recordCustomer(Customer customer) {
        Individual individualResponseEntity = this.proxy.recordIndividual(customer.getIndividual());
        Address addressResponseEntity = this.proxy.recordAddress(customer.getAddress());
        Account accountResponseEntity = this.proxy.createAccount(individualResponseEntity.getIndividualId(), addressResponseEntity.getAddressId());

        return formCustomerResponse(individualResponseEntity, addressResponseEntity, accountResponseEntity);
    }

    private org.subcon.model.Customer formCustomerResponse(Individual individual, Address address, Account account) {
        AccountStatus accountStatus = account.getAccountOpeningStatus();
        return new org.subcon.model.Customer(individual, address, accountStatus);
    }

    @Override
    public List<org.subcon.model.Customer> getCustomer() {
        return this.proxy.getCustomer();
    }

    @Override
    public org.subcon.model.Customer updateCustomer(Customer customer) {
        return this.proxy.updateCustomer(customer);
    }

    @Override
    public String deleteCustomer(String customerId) {
        return this.proxy.deleteCustomer(customerId);
    }
}
