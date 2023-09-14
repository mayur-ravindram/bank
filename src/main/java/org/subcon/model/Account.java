package org.subcon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private int accountId;

    private int individualId;
    private int addressId;

    private AccountStatus accountOpeningStatus;
}
