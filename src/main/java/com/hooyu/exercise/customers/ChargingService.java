package com.hooyu.exercise.customers;

import com.hooyu.exercise.customers.dao.HardcodedListOfCustomerCreditsImpl;
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.charging.ChargingException;
import net.icdpublishing.exercise2.myapp.charging.services.ImaginaryChargingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Subrahmanyam on 23/09/2018.
 */
public class ChargingService extends ImaginaryChargingService {



    private HardcodedListOfCustomersImpl hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
    int availableCredits;
    private Customer customer;
    private HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits = new HardcodedListOfCustomerCreditsImpl();
    public void charge(String email, int numberOfCredits) throws ChargingException {
        //TODO
        customer =hardcodedListOfCustomers.findCustomerByEmailAddress(email);
        hardcodedListOfCustomerCredits.poulateCustomerCredits();
        if(customer.getCustomType().toString().contains("PREMIUM"))
        {
            //Reducing Credits by number of records received
            availableCredits=hardcodedListOfCustomerCredits.getCustomerCredits(email);
            availableCredits=availableCredits-numberOfCredits;
            hardcodedListOfCustomerCredits.setCustomersCredits(email,availableCredits);

            if(numberOfCredits <=0)
            {
                throw new ChargingException("Credits out of balance. Please topup");
            }

        }


    }


}
