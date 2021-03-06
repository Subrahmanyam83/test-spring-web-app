package com.hooyu.exercise.customers.dao;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.customers.domain.CustomerType;
import net.icdpublishing.exercise2.myapp.charging.ChargingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HardcodedListOfCustomersImpl implements CustomerDao {

	private static Map<String,Customer> customers = new HashMap<>();
	Customer customer;

	public HardcodedListOfCustomersImpl() {
		customers.put("john.doe@192.com", createDummyCustomer("john.doe@192.com", "John", "Doe", CustomerType.PREMIUM));
		customers.put("sally.smith@192.com", createDummyCustomer("sally.smith@192.com", "Sally", "Smith", CustomerType.PREMIUM));
		customers.put("harry.lang@192.com", createDummyCustomer("harry.lang@192.com", "Harry", "Lang", CustomerType.NON_PAYING));
	 }
	
	public Customer findCustomerByEmailAddress(String email)  {
		customer = customers.get(email);
		if(customer == null) {
			throw new CustomerNotFoundException("Invalid customer");
		}
		return customer;
	}

	public ArrayList<Customer> getCustomers()
	{
		ArrayList<Customer> allCustomers = new ArrayList<Customer>();
		for (Customer cust:customers.values()) {
			allCustomers.add(cust);
		}
		return allCustomers;
	}

	private Customer createDummyCustomer(String email, String forename, String surname, CustomerType type) {
		Customer c = new Customer();
		c.setEmailAddress(email);
		c.setForename(forename);
		c.setSurname(surname);
		c.setCustomType(type);
		return c;
	}
}