package org.subcon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.subcon.dto.Address;
import org.subcon.dto.Customer;
import org.subcon.dto.Individual;
import org.subcon.service.CustomerService;

import java.io.FileReader;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @MockBean
    private CustomerService service;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void recordNewCustomer() throws Exception {
        Customer customer = buildCustomer(buildIndividual(), buildAddress());

        given(service.recordCustomer(customer))
                .willAnswer(invocation -> new org.subcon.model.Customer());

        mockMvc.perform(post("/record-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    void testRecordCustomer_BadRequestErrorForEmptyAddress() throws Exception {
        Customer customer = buildCustomer(buildIndividual(), null);
        given(service.recordCustomer(customer))
                .willThrow(new RuntimeException());

        mockMvc.perform(post("/record-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.errorMessage").value("Address is mandatory"))
                .andExpect(jsonPath("$.errorCode").value("NotNull"));
    }

    @Test
    void testRecordCustomer_BadRequestErrorForEmptyIndividual() throws Exception {
        Customer customer = buildCustomer(null, buildAddress());
        given(service.recordCustomer(customer))
                .willThrow(new RuntimeException());

        mockMvc.perform(post("/record-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.errorMessage").value("Individual is mandatory"))
                .andExpect(jsonPath("$.errorCode").value("NotNull"));
    }

    @Test
    void listCustomers() throws Exception {

        org.subcon.model.Customer customers = objectMapper.readValue(new FileReader("src/test/resources/MOCK_DATA.json"), org.subcon.model.Customer.class);

        given(service.listCustomer()).willReturn(List.of(customers));
        mockMvc.perform(get("/get-customers"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void updateCustomers() throws Exception {
        Customer customer = buildCustomer(buildIndividual(), buildAddress());
        given(service.updateCustomer(customer))
                .willAnswer(invocation -> new org.subcon.model.Customer());

        mockMvc.perform(put("/update-customer")
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomers() throws Exception{
        given(service.deleteCustomer(anyString())).willReturn("200");

        mockMvc.perform(delete("/delete-customer/{customerId}", "1"))
                .andExpect(status().isOk());
    }

    private Customer buildCustomer(Individual individual, Address address) {
        return Customer.builder()
                .individual(individual)
                .address(address)
                .build();
    }

    private Address buildAddress() {
        return Address.builder()
                .houseNumber(123)
                .buildingName("xyz building")
                .streetName("abc street")
                .city("pqr city")
                .country("lmn country")
                .build();
    }

    private Individual buildIndividual() {
        return Individual.builder()
                .age(30)
                .firstName("John")
                .middleName("Harry")
                .lastName("Doe")
                .build();
    }
}