package org.subcon.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Customer {
    @NotNull(message = "Individual is mandatory")
    private Individual individual;

    @NotNull(message = "Address is mandatory")
    private Address address;
}
