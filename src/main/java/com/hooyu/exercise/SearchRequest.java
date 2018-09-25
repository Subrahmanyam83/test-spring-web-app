package com.hooyu.exercise;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.exceptions.InvalidSearchRequest;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.loader.DataLoader;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.*;

public class SearchRequest implements SearchEngineRetrievalService {
	private SimpleSurnameAndPostcodeQuery query;
	private Customer customer;
	String surname;
	String postcode;
	private DataLoader dataLoader= new DataLoader();

	public SearchRequest(SimpleSurnameAndPostcodeQuery query, Customer customer) {
		this.query = query;
		this.customer = customer;
	}

	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query) {
        Collection<Record> matchedRecords = new LinkedList<Record>();

		String entered_surname = query.getSurname();
		String entered_postcode =query.getPostcode();
		Collection<Record> records =dataLoader.loadAllDatasets();
		for (Record r:records
			 ) {
			 surname=r.getPerson().getSurname().toString();
			 postcode=r.getPerson().getAddress().getPostcode().toString();
			if((surname.equalsIgnoreCase(entered_surname))&&(postcode.equalsIgnoreCase(entered_postcode))) {
				matchedRecords.add(r);
			}
		}
		if(matchedRecords.size() == 0)
		{
			throw new InvalidSearchRequest("No records matching with the provided Surname and Postcode");
		}
		return matchedRecords;
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