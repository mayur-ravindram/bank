package org.subcon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private int houseNumber;
    private String buildingName;
    private String streetName;
    private String city;
    private String country;
}
