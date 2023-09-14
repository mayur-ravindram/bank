package org.subcon.util;

import org.subcon.model.AccountStatus;
import org.subcon.model.Individual;

public class CommonUtilities {
    public static AccountStatus getAccountStatus(Individual individual) {
        return AccountStatus.values()[individual.getFirstName().length()%AccountStatus.values().length];
    }
}
