package org.subcon.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Customer {

    @Valid
    @NotNull(message = "individual should not be empty")
    private Individual individual;

    @Valid
    @NotNull(message = "address should not be empty")
    private Address address;
}
