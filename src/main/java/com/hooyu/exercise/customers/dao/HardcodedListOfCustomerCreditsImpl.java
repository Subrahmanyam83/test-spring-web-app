package com.hooyu.exercise.customers.dao;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.customers.domain.CustomerCredits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Subrahmanyam on 24/09/2018.
 */
public class HardcodedListOfCustomerCreditsImpl implements CustomerCreditsDao{

    private static Map<Customer,Integer> customersCredits = new HashMap<>();
    private HardcodedListOfCustomersImpl hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();

    @Override
    public int getCustomerCredits(String email)
    {
        poulateCustomerCredits();
        for (Customer c:hardcodedListOfCustomers.getCustomers()
                )
        {
            if(c.getEmailAddress().toString().equalsIgnoreCase(email))
            {
             return customersCredits.get(c);
            }

        }
        return 0;
    }

    public void poulateCustomerCredits()
    {
        for (Customer c:hardcodedListOfCustomers.getCustomers()
        ) {
            if(c.getCustomType().toString().contains("PREMIUM"))
            {
                customersCredits.put(c,192);

            }
            else
            {
                customersCredits.put(c,0);
            }

        }
    }


    /*This method is not required - Check once*/

   /* public void setCustomersCredits(String email,int customerCredits)
    {
        for (Customer c:hardcodedListOfCustomers.getCustomers()
                )
        {
            if(c.getEmailAddress().toString().equalsIgnoreCase(email))
            {
             customersCredits.put(c,customerCredits);
            }

        }
    }*/
}
