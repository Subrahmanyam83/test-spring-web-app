package com.hooyu.exercise.customers.dao;

import com.hooyu.exercise.customers.domain.Customer;

public interface CustomerDao {
	Customer findCustomerByEmailAddress(String email) throws RuntimeException;
}
