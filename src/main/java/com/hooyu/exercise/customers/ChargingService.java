package com.hooyu.exercise.customers;

import com.hooyu.exercise.customers.dao.HardcodedListOfCustomerCreditsImpl;
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.services.ImaginaryChargingService;

public class ChargingService extends ImaginaryChargingService {

    private HardcodedListOfCustomersImpl hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
    private HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits = new HardcodedListOfCustomerCreditsImpl();
    public int availableCredits;

    /*This method will charge the customer with some credit points*/
    @Override
    public void charge(String email, int numberOfCredits) {
        Customer customer=hardcodedListOfCustomers.findCustomerByEmailAddress(email);
        hardcodedListOfCustomerCredits.populateCustomerCredits();
        if(customer.getCustomType().toString().contains("PREMIUM")) {
            availableCredits=hardcodedListOfCustomerCredits.getCustomerCredits(email);
            availableCredits=availableCredits-numberOfCredits;
            hardcodedListOfCustomerCredits.setCustomersCredits(email,availableCredits);
            if(numberOfCredits <=0) {
                throw new ChargingException("Credits out of balance. Please topup");
            }
        }
    }
}
