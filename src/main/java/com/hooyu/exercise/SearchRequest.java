package com.hooyu.exercise;

import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.exceptions.InvalidSearchRequest;
import com.sun.org.apache.regexp.internal.RE;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.loader.DataLoader;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Array;
import java.util.*;

public class SearchRequest implements SearchEngineRetrievalService {
	private SimpleSurnameAndPostcodeQuery query;
	private Customer customer;
	String surname;
	String postcode;

	private DataLoader dataLoader= new DataLoader();
	private HardcodedListOfCustomersImpl hardcodedListOfCustomers = new HardcodedListOfCustomersImpl();
	private SearchEngineRetrievalService searchEngineRetrievalService;
	public SearchRequest(SimpleSurnameAndPostcodeQuery query, Customer customer) {

		this.query = query;
		this.customer = customer;


	}

	//Method to search for a Record
	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query) {
        Collection<Record> matchedRecords = new LinkedList<Record>();

		String entered_surname = query.getSurname();
		String entered_postcode =query.getPostcode();
		Collection<Record> records =dataLoader.loadAllDatasets();
		for (Record r:records
			 ) {
			//System.out.print("Insdie for each records loop");
			 surname=r.getPerson().getSurname().toString();
			 postcode=r.getPerson().getAddress().getPostcode().toString();
			 //System.out.print(surname +" "+entered_surname+" "+postcode+" "+entered_postcode);
			if((surname.equalsIgnoreCase(entered_surname))&&(postcode.equalsIgnoreCase(entered_postcode)))
			{
				//System.out.print("Surname and Postcode are matching in roecords");
				matchedRecords.add(r);
			}

		}
		if(matchedRecords.size() == 0)
		{
			throw new InvalidSearchRequest("No records matching with the provided Surname and Postcode");
		}
		//System.out.print("Matched Records have been received and the size is: "+matchedRecords.size());
		return matchedRecords;
	}

/*
	//Method to search for Customer based on the surname input given in search form
	public Customer searchCustomerDetailsFromSearchForm(String surname)
	{
		//System.out.print("Inside search class");

		for (Customer c:hardcodedListOfCustomers.getCustomers())
		{
			//System.out.print("Surname of cus is; "+c.getSurname() +" and surname entered is; "+ surname);
			if(c.getSurname().toString().equals(surname))
			{
				//System.out.print("inside If --- they are matched");
              return c;
			}
		}
		return null;
	}
*/
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