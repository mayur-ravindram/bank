package org.subcon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int addressId;
    private int houseNumber;
    private String buildingName;
    private String streetName;
    private String city;
    private String country;
}
