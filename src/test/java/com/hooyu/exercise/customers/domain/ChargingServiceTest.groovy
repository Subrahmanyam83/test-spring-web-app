package com.hooyu.exercise.customers.domain

import com.hooyu.exercise.customers.ChargingService
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomerCreditsImpl
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl
import com.hooyu.exercise.exceptions.ChargingException
import javafx.beans.binding.When
import spock.lang.Specification

/**
 * Created by Subrahmanyam on 24/09/2018.
 */
class ChargingServiceTest extends Specification{

    private ChargingService chargingService;
    private HardcodedListOfCustomersImpl hardcodedListOfCustomers;
    HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits

    def setup() {
        chargingService = new ChargingService();
        hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
        hardcodedListOfCustomerCredits = new HardcodedListOfCustomerCreditsImpl();
    }

    def "FInds customer"() {
        when:
        hardcodedListOfCustomers.findCustomerByEmailAddress("john.doe@192.com");


        then:
        expectedCustomer
    }
    def "Get Credits for the customer" (){


        when:
        expectedCustomer.getCustomType().toString().contains("PREMIUM")

        then:
        int

    }
    def "Get Zero Credits" (){


        when:
        "Get Credits for the customer" <0

        then:
        thrown(ChargingException)

    }
    def getExpectedCustomer() {
        Customer expectedCustomer = new Customer();
        expectedCustomer.setEmailAddress("john.doe@192.com");
        expectedCustomer.setForename("John");
        expectedCustomer.setSurname("Doe");
        expectedCustomer.setCustomType(CustomerType.PREMIUM);
        return expectedCustomer;
    }
}
