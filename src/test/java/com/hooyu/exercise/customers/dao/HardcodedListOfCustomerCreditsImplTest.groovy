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
        when:

        hardcodedListOfCustomerCredits.getCustomerCredits("john.doe@192.com");

        then:
        int;
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
