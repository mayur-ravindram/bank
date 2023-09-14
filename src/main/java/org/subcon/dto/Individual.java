package org.subcon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Individual {
    @NotBlank(message = "first name should not be blank")
    private String firstName;

    private String middleName;

    @NotBlank(message = "last name should not be blank")
    private String lastName;

    @NotNull(message = "age should not be null")
    private int age;

    private int addressId;
}
