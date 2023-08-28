package org.subcon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Individual {
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
}
