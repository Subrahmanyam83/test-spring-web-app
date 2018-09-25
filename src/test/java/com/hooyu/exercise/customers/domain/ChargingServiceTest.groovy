package com.hooyu.exercise.customers.domain

import com.hooyu.exercise.customers.ChargingService
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomerCreditsImpl
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl
import spock.lang.Specification
import net.icdpublishing.exercise2.myapp.charging.ChargingException;

class ChargingServiceTest extends Specification{

    private ChargingService chargingService;
    private HardcodedListOfCustomersImpl hardcodedListOfCustomers;
    HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits
    String joe_email="john.doe@192.com";
    String sally_email="sally.smith@192.com";
    String harry_email="harry.lang@192.com";

    def setup() {
        chargingService = new ChargingService();
        hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
        hardcodedListOfCustomerCredits = new HardcodedListOfCustomerCreditsImpl();
    }

    def "charge credits for premium customer less than the current credits"() {
        when:
        chargingService.charge(joe_email,20)

        then:
        assert chargingService.availableCredits.equals(172)
    }


    def "charge credits for premium customer equal to the current credits"() {
        when:
        chargingService.charge(joe_email,192)

        then:
        assert chargingService.availableCredits.equals(0)
        assert new ChargingException("Credits out of balance. Please topup")
    }


    def "charge premium customers with more than the current credits" (){
        when:
        chargingService.charge(sally_email,200)

        then:
        assert chargingService.availableCredits<0
    }

    def "charge some credit for non-premium customer" (){
        when:
        chargingService.charge(harry_email,20)

        then:
        assert new ChargingException("Credits out of balance. Please topup")
    }


    def "charge non-premium customers with zero credits"(){
        when:
        chargingService.charge(harry_email,0)

        then:
        assert chargingService.availableCredits.equals(0)
    }
}