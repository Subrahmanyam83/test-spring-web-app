package com.hooyu.exercise.customers.dao

import com.hooyu.exercise.customers.domain.Customer
import spock.lang.Specification

/**
 * Created by Subrahmanyam on 24/09/2018.
 */
class HardcodedListOfCustomerCreditsImplTest extends Specification {
    HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits;
    HardcodedListOfCustomersImpl hardcodedListOfCustomers;
    def setup() {
        hardcodedListOfCustomerCredits  = new HardcodedListOfCustomerCreditsImpl();
        hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
    }

    def "should return credits available to the customer"() {

        when: "I get the credit scores based on the emails"

        String email_1="john.doe@192.com";
        String email_2="sally.smith@192.com";
        String email_3="harry.lang@192.com";

        int credit_1 = hardcodedListOfCustomerCredits.getCustomerCredits(email_1);
        int credit_2 = hardcodedListOfCustomerCredits.getCustomerCredits(email_2);
        int credit_3 = hardcodedListOfCustomerCredits.getCustomerCredits(email_3);

        then: "I get the correct credit score"
        assert credit_1==credit_2!=credit_3;
    }

    def getExpectedCustomers() {
        Collection<Customer> customerArrayList = new ArrayList<Customer>();
        Customer expectedCustomer1 = new Customer();
        Customer expectedCustomer2 = new Customer();
        expectedCustomer1.setEmailAddress("john.doe@192.com");
        expectedCustomer1.setForename("John");
        expectedCustomer1.setSurname("Doe");
        expectedCustomer1.setCustomType(CustomerType.PREMIUM);
        expectedCustomer2.setEmailAddress("sally.smith@192.com");
        expectedCustomer2.setForename("sally");
        expectedCustomer2.setSurname("smith");
        expectedCustomer2.setCustomType(CustomerType.PREMIUM);
        customerArrayList.add(expectedCustomer1);
        customerArrayList.add(expectedCustomer2);
        return customerArrayList;
    }
}
