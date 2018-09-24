package com.hooyu.exercise.controllers

import com.hooyu.exercise.customers.CustomerService
import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.mock.web.MockHttpSession
import spock.lang.Specification
import org.springframework.mock.web.MockHttpServletRequest

import javax.servlet.http.HttpSession

/**
 * Created by Subrahmanyam on 24/09/2018.
 */
class SigninControllerTest extends Specification{
    //SignInController controller;
    SimpleSurnameAndPostcodeQuery query;
    MockHttpSession mockHttpSession;

    //@Autowired
    SignInController controller;

    @Autowired
    private CustomerService customer_details


    def setup() {
        customer_details= new CustomerService();
        controller = new SignInController();
        query=new SimpleSurnameAndPostcodeQuery("Smith","sw6 2bq");
        mockHttpSession = new MockHttpSession();

    }





    def "authorized customer on sigining in, search form must come"() {

        when://    SignInController controller;
        SignInController mockSignInController = Mockito.mock(SignInController.class);
        CustomerService customer_details = Mockito.mock(CustomerService.class);
        Mockito.when(customer_details.findCustomerByEmailAddress("john.doe@192.com")).thenReturn(expectedCustomer);

        and:
        String returnValue = mockSignInController.signin_success("john.doe@192.com",mockHttpSession)

        then:
        assert returnValue.equals("search.html");
    }
/*

    def "Returning Records in Tabular format"() {
        when:
        controller.searchDetails("john.doe@192.com","sw6 2bq")

        then:
        "CustomerDetails.html"
    }



    def "Find records for provided Surname + Postcode"() {
        when:
        controller.processFetchedDetailsFromURI("Smith","sw6 2bq")

        then:
        Collection<Record> records
    }
*/

    def "Process records based on customer and returns credits"() {
        when:
        controller.processRecordsforCredits(query,expectedCustomer)

        then:
        Collection<Record> records
    }

    def getExpectedCustomer() {
        Customer expectedCustomer = new Customer();
        expectedCustomer.setEmailAddress("john.doe@192.com");
        expectedCustomer.setForename("John");
        expectedCustomer.setSurname("Doe");
        expectedCustomer.setCustomType(CustomerType.PREMIUM);
        return expectedCustomer;
    }

    /*def getExpectedCustomers() {
        Collection<Customer> customerArrayList = new ArrayList<Customer>();
        Customer expectedCustomer1 = new Customer();
        Customer expectedCustomer2 = new Customer();
        expectedCustomer1.setEmailAddress("john.doe@192.com");
        expectedCustomer1.setForename("John");
        expectedCustomer1.setSurname("Doe");
        expectedCustomer1.setCustomType(CustomerType.PREMIUM);
        expectedCustomer2.setEmailAddress("sally.smith@192.com");
        expectedCustomer2.setForename("sally");
        expectedCustomer2.setSurname("smith");
        expectedCustomer2.setCustomType(CustomerType.PREMIUM);
        customerArrayList.add(expectedCustomer1);
        customerArrayList.add(expectedCustomer2);
        return customerArrayList;*/
    }


