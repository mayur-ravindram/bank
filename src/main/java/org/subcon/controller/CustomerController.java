package org.subcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.subcon.dto.Customer;
import org.subcon.service.CustomerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/record-customer")
    ResponseEntity<org.subcon.model.Customer> recordNewCustomer(@Valid @RequestBody Customer customerData) {
        org.subcon.model.Customer recordedCustomer = customerService.recordCustomer(customerData);

        return new ResponseEntity<>(recordedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/get-customers")
    ResponseEntity<List<org.subcon.model.Customer>> listCustomers() {
        List<org.subcon.model.Customer> fetchedCustomers = customerService.listCustomer();

        return ResponseEntity.ok(fetchedCustomers);
    }

    @PutMapping("/update-customer")
    ResponseEntity<org.subcon.model.Customer> updateCustomers(@Valid @RequestBody Customer customer) {
        org.subcon.model.Customer updatedCustomer = customerService.updateCustomer(customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/delete-customer/{customerId}")
    ResponseEntity<String> deleteCustomers(@PathVariable String customerId) {
        String deletedCustomer = customerService.deleteCustomer(customerId);

        return ResponseEntity.ok(deletedCustomer);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<Map<String,String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<Map<String,String>> errorList = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("errorCode", error.getCode());
            errorMap.put("errorMessage", error.getDefaultMessage());
            errorMap.put("field", error.getField());
            errorList.add(errorMap);
        });
        return errorList;
    }
}
