package com.hooyu.exercise;

import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Array;
import java.util.*;

public class SearchRequest {
	private SimpleSurnameAndPostcodeQuery query;
	private Customer customer;
	private HardcodedListOfCustomersImpl hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();






	public SearchRequest(SimpleSurnameAndPostcodeQuery query, Customer customer) {

		this.query = query;
		this.customer = customer;


	}
	//Method to search for Customer based on the surname input given in search form
	public Customer searchCustomerDetailsFromSearchForm(String surname)
	{
		System.out.print("Inside search class");
		for (Customer c:hardcodedListOfCustomers.getCustomers())
		{
			if(c.getSurname() == surname)
			{
              return c;
			}
		}
		return null;
	}

	public SimpleSurnameAndPostcodeQuery getQuery() {
		return query;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	@Override
	public boolean equals(Object obj) {	
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {		
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}