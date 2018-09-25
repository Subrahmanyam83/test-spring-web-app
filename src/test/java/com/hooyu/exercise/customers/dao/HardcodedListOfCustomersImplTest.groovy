package com.hooyu.exercise.customers.dao

import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import spock.lang.Specification;

class HardcodedListOfCustomersImplTest extends Specification {

	private HardcodedListOfCustomersImpl customerImpl;
	String joe_email="john.doe@192.com";

	def setup() {
		customerImpl = new HardcodedListOfCustomersImpl();
	}

	def "retrieve customer details based on email"() {
		given: "I have details of Joe"
		String forename= "John"
		String surname= "Doe"
		String customerType = CustomerType.PREMIUM;
		when:
		def customer = customerImpl.findCustomerByEmailAddress(joe_email);

		then:
		assert customer.surname.equals(surname)
		assert customer.forename.equals(forename)
		assert customer.customType.name().equals(customerType)
	}

	def "retrieve customer details for a non-existent email"() {
		given: "I have an email for a non-existing customer"
		String email = "abc@xyz.com"

		when:
		customerImpl.findCustomerByEmailAddress(email);

		then:
		thrown CustomerNotFoundException
	}

	def "get the count of the customers"() {

		when:
		def customers = customerImpl.getCustomers();

		then:
		assert customers.size().equals(3)
	}
}
