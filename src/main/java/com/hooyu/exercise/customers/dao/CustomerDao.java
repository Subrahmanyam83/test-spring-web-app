package com.hooyu.exercise.customers.dao;

import com.hooyu.exercise.customers.domain.Customer;
import org.springframework.context.annotation.Bean;


public interface CustomerDao {

	/**
	 * 
	 * @param email
	 * @return
	 * @throws CustomerNotFoundException
	 */

	Customer findCustomerByEmailAddress(String email) throws CustomerNotFoundException;
}
