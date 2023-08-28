package org.subcon.external.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.subcon.dto.Address;
import org.subcon.dto.Individual;
import org.subcon.model.Customer;

import java.util.ArrayList;
import java.util.List;

@Component
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
        org.subcon.model.Address response = this.databaseClient.postForObject(
                DATABASE_URL.concat("/record-address"),
                address,
                org.subcon.model.Address.class);

        return response;
    }

    public org.subcon.model.Individual recordIndividual(Individual individual) {
        org.subcon.model.Individual response = this.databaseClient.postForObject(
                DATABASE_URL.concat("/record-individual"),
                individual,
                org.subcon.model.Individual.class);

        return response;
    }

    public List<Customer> getCustomer() {
        org.subcon.model.Address[] addressResponseEntity = this.databaseClient.getForObject(
                DATABASE_URL.concat("/list-address"),
                org.subcon.model.Address[].class);
        org.subcon.model.Individual[] individualResponseEntity = this.databaseClient.getForObject(
                DATABASE_URL.concat("/list-individual"),
                org.subcon.model.Individual[].class);

        List<Customer> customers = new ArrayList<>();

        for (org.subcon.model.Address address: addressResponseEntity) {
            for(org.subcon.model.Individual individual: individualResponseEntity) {
                if(address.getAddressId()== individual.getIndividualId()) {
                    customers.add(new Customer(individual, address));
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

        try{
            this.databaseClient.delete(DATABASE_URL.concat("/delete-address/"+customerId));
            this.databaseClient.delete(DATABASE_URL.concat("/delete-individual/"+customerId));
        } catch (Exception e) {
            return null;
        }
        return "200";
    }
}
