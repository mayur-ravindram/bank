package org.subcon.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Address {
    @NotNull(message = "house number should not be null")
    private int houseNumber;
    @NotBlank(message = "building name should not be blank")
    private String buildingName;
    @NotBlank(message = "street name should not be blank")
    private String streetName;
    @NotBlank(message = "city should not be blank")
    private String city;
    @NotBlank(message = "country should not be blank")
    private String country;
}
