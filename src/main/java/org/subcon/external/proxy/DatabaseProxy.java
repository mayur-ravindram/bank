package org.subcon.external.proxy;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.subcon.dto.Address;
import org.subcon.dto.Individual;
import org.subcon.model.Account;
import org.subcon.model.AccountStatus;
import org.subcon.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
@Log4j2
public class DatabaseProxy {

    @Value("${db.endpoint}")
    private String DATABASE_URL;

    @Autowired
    private final RestTemplate databaseClient;

    @Autowired
    public DatabaseProxy(RestTemplate restTemplate) {
        this.databaseClient = restTemplate;
    }

    public org.subcon.model.Address recordAddress(Address address) {
        return this.databaseClient.postForObject(
                DATABASE_URL.concat("/record-address"),
                address,
                org.subcon.model.Address.class);

    }

    public org.subcon.model.Individual recordIndividual(Individual individual) {
        if(individual.getAge() < 18 || individual.getAge() > 59) individual.setAge(new Random().nextInt(18, 59));
        return this.databaseClient.postForObject(
                DATABASE_URL.concat("/record-individual"),
                individual,
                org.subcon.model.Individual.class);

    }

    public List<Customer> getCustomer() {
        org.subcon.model.Address[] addressResponseEntity = this.databaseClient.getForObject(
                DATABASE_URL.concat("/list-address"),
                org.subcon.model.Address[].class);
        org.subcon.model.Individual[] individualResponseEntity = this.databaseClient.getForObject(
                DATABASE_URL.concat("/list-individual"),
                org.subcon.model.Individual[].class);

        List<Customer> customers = new ArrayList<>();

        for (org.subcon.model.Address address : Objects.requireNonNull(addressResponseEntity)) {
            for (org.subcon.model.Individual individual : Objects.requireNonNull(individualResponseEntity)) {
                if (address.getAddressId() == individual.getIndividualId()) {
                    Account account = new Account();
                    final Customer customer;
                    int individualId = individual.getIndividualId();
                    account.setIndividualId(individualId);
                    String accountUrl = DATABASE_URL.concat("/get-account/") + individualId;
                    Account accountResponseEntity = this.databaseClient.getForObject(
                            accountUrl,
                            Account.class);
                    AccountStatus accountOpeningStatus = Objects.requireNonNull(accountResponseEntity).getAccountOpeningStatus();
                    customer = new Customer(individual, address, accountOpeningStatus);
                    customers.add(customer);
                }
            }
        }

        return customers;
    }

    public Customer updateCustomer(org.subcon.dto.Customer customer) {
        HttpEntity<org.subcon.dto.Customer> httpEntity = new HttpEntity<>(customer);

        ResponseEntity<Customer> updatedCustomer = this.databaseClient.exchange(DATABASE_URL,
                HttpMethod.PUT,
                httpEntity,
                Customer.class);
        return updatedCustomer.getBody();
    }

    public String deleteCustomer(String customerId) {

        try {
            this.databaseClient.delete(DATABASE_URL.concat("/delete-address/" + customerId));
            this.databaseClient.delete(DATABASE_URL.concat("/delete-individual/" + customerId));
        } catch (Exception e) {
            return null;
        }
        return "200";
    }

    public Account createAccount(int individualId, int addressId) {
        Account newAccount = new Account();
        newAccount.setIndividualId(individualId);
        newAccount.setAddressId(addressId);
        return this.databaseClient.postForObject(
                DATABASE_URL.concat("/open-account"),
                newAccount,
                org.subcon.model.Account.class);
    }
}
