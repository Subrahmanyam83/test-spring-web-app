package com.hooyu.exercise.customers.dao

import spock.lang.Specification

class HardcodedListOfCustomerCreditsImplTest extends Specification {
    HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits;
    HardcodedListOfCustomersImpl hardcodedListOfCustomers;

    def setup() {
        hardcodedListOfCustomerCredits  = new HardcodedListOfCustomerCreditsImpl();
        hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
    }

    def "should return credits available to the customer"() {
        when: "I retrieve the credit scores based on the emails"
        String joe_email="john.doe@192.com";
        String sally_email="sally.smith@192.com";
        String harry_email="harry.lang@192.com";

        int credit_1 = hardcodedListOfCustomerCredits.getCustomerCredits(joe_email);
        int credit_2 = hardcodedListOfCustomerCredits.getCustomerCredits(sally_email);
        int credit_3 = hardcodedListOfCustomerCredits.getCustomerCredits(harry_email);

        then: "I recieve the correct credit score for each customer"
        assert credit_1==credit_2!=credit_3;
    }
}
