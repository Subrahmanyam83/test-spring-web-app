package com.hooyu.exercise.customers.domain;

/**
 * Created by Subrahmanyam on 24/09/2018.
 */
public class CustomerCredits {
    private Customer customer;
    private int customerCredits;

    public CustomerCredits(Customer customer, int customerCredits) {
        this.customer = customer;
        this.customerCredits = customerCredits;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerCredits() {
        return customerCredits;
    }

    public void setCustomerCredits(int customerCredits) {
        this.customerCredits = customerCredits;
    }
}
