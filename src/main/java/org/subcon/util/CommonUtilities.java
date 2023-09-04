package org.subcon.util;

import org.subcon.model.AccountStatus;
import org.subcon.model.Individual;

public class CommonUtilities {
    public static AccountStatus getAccountStatus(Individual individual) {
        System.out.printf("AccountStatus.values()[%s mod %d]",
                individual.getFirstName().length(), AccountStatus.values().length);
        return AccountStatus.values()[individual.getFirstName().length()%AccountStatus.values().length];
    }
}
