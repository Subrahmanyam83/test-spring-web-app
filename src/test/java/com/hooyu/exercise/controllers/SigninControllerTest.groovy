package com.hooyu.exercise.controllers

import com.hooyu.exercise.customers.CustomerService
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl
import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.mock.web.MockHttpSession
import spock.lang.Specification
import org.springframework.mock.web.MockHttpServletRequest
import javax.servlet.http.HttpSession;
import org.junit.runner.*;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Subrahmanyam on 24/09/2018.
 */

//@RunWith(PowerMockRunner.class)
@PrepareForTest(value = CustomerService.class)
class SigninControllerTest extends Specification{
    //SignInController controller;
    SimpleSurnameAndPostcodeQuery query;
    MockHttpSession mockHttpSession;

    //@Autowired
    SignInController controller;





    def setup() {
        //customer_details= new CustomerService();
        controller = new SignInController();
        query=new SimpleSurnameAndPostcodeQuery("Smith","sw6 2bq");
        mockHttpSession = new MockHttpSession();

    }




//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();


//    def "authorized customer on sigining in, search form must come"() {
//
//        when:
//
//       // SignInController mockSignInController = mock(SignInController.class);
//        SignInController mockSignInController = new SignInController();
//        CustomerService customer_details = mock(CustomerService.class);
//
//        HardcodedListOfCustomersImpl hardcodedListOfCustomersMock = mock(HardcodedListOfCustomersImpl);
//        when(hardcodedListOfCustomersMock.findCustomerByEmailAddress(anyString())).thenReturn(getExpectedCustomer())
//
//        and:
//        String returnValue = mockSignInController.signin_success("john.doe@192.com",mockHttpSession)
//
//        then:
//        assert returnValue.equals("search.html");
//    }

    def "Test Subu test"() {

        when: "this is dalling "

        SignInController signInController = new SignInController();

        CustomerService spyCustomerService = PowerMockito.mock(CustomerService.class);
        PowerMockito.whenNew(CustomerService.class).withNoArguments().thenReturn(spyCustomerService);

        PowerMockito.when(spyCustomerService.findCustomerByEmailAddress(anyString())).thenReturn(getExpectedCustomer())

        and:
        String returnValue = signInController.signin_success("john.doe@192.com",mockHttpSession)

        then:
        assert returnValue.equals("search.html");
    }


    def "Test subu test 1"(){

        when:
        int a =10;

        then:
        a>4

    }
/*

    def "Returning Records in Tabular format"() {
        when:
        controller.searchDetails("john.doe@192.com","sw6 2bq")

        then:
        "CustomerDetails.html"
    }


*/


    def "Find records for provided Surname + Postcode"() {
        given: "I have record details for Surname - Smith and postcode - sw6 2bq"
        Arrays arrays = ['']
        arrays.sort();

        when:
        Collection<Record> records =controller.processFetchedDetailsFromURI("Smith","sw6 2bq")

        then:
        assert records.toArray().toSorted().equals()

    }


//    def "Process records based on customer and returns credits"() {
//        when:
//        controller.processRecordsforCredits(query,expectedCustomer)
//
//        then:
//        Collection<Record> records
//    }

    def getExpectedCustomer() {
        Customer expectedCustomer = new Customer();
        expectedCustomer.setEmailAddress("john.doe@192.com");
        expectedCustomer.setForename("John");
        expectedCustomer.setSurname("Doe");
        expectedCustomer.setCustomType(CustomerType.PREMIUM);
        return expectedCustomer;
    }

    def getExpectedCustomers() {
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
        return customerArrayList;
    }


