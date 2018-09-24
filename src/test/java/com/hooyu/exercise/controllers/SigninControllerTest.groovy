package com.hooyu.exercise.controllers

import com.hooyu.exercise.customers.CustomerService
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl
import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
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
import org.powermock.api.mockito.PowerMockito

import java.lang.reflect.Field;

/**
 * Created by Subrahmanyam on 24/09/2018.
 */

@PrepareForTest(value = CustomerService.class)
class SigninControllerTest extends Specification {

    SignInController controller;
    SimpleSurnameAndPostcodeQuery query;
    MockHttpSession mockHttpSession;


    def setup() {
        //customer_details= new CustomerService();
        controller = new SignInController();
        query = new SimpleSurnameAndPostcodeQuery("Smith", "sw6 2bq");
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

    def "authorized customer on sigining in, search form must come"() {

        when: "this is dalling "

        SignInController signInController = new SignInController();

        CustomerService spyCustomerService = PowerMockito.mock(CustomerService.class);
        PowerMockito.whenNew(CustomerService.class).withNoArguments().thenReturn(spyCustomerService);

        PowerMockito.when(spyCustomerService.findCustomerByEmailAddress(asType(String.class))).thenReturn(getExpectedCustomer())

        and:
        String returnValue = signInController.signin_success("john.doe@192.com", mockHttpSession)

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


*/


    def "Find records for provided Surname + Postcode"() {
        given: "I have record details for Surname - Smith and postcode - sw6 2bq"

        String surname= "Smith";
        String postCode = "sw6 2bq"


        Field f = SignInController.getDeclaredField("cust"); //NoSuchFieldException
        f.setAccessible(true);


        Customer customer =(Customer)f.get(controller);
        printf "fd"+customer;
        customer=getExpectedCustomer();


        when:
        Collection<Record> recordCollection = controller.fetchedDetailsFromURI(surname,postCode);

        then:
        assert recordCollection.sort().equals(getExpectedRecords().sort());

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

    def getExpectedRecords() {
        Collection<Record> recordCollection = new ArrayList<Record>();
        Person p1 = new Person();
        p1.setForename("Alfred");
        p1.setMiddlename("Duncan");
        p1.setSurname("Smith");
        p1.setTelephone("07702828333");

        Address address1 = new Address();
        address1.setBuildnumber("1");
        address1.setPostcode("sw6 2bq");
        address1.setStreet("william morris way");
        address1.setTown("London");
        p1.setAddress(address1);

        Set<SourceType> sources1 = new HashSet<SourceType>();
        sources1.add(SourceType.BT);
        sources1.add(SourceType.DNB);
        sources1.add(SourceType.ELECTORAL_ROLL);
        Record r1 = new Record(p1,sources1);

        Person p2 = new Person();
        p2.setForename("Mary");
        p2.setMiddlename("Ann");
        p2.setSurname("Smith");
        p2.setTelephone("07702811339");

        Address address2 = new Address();
        address2.setBuildnumber("13");
        address2.setPostcode("sw6 2bq");
        address2.setStreet("william morris way");
        address2.setTown("London");
        p2.setAddress(address2);

        Set<SourceType> sources2 = new HashSet<SourceType>();
        sources2.add(SourceType.BT);
        Record r2 = new Record(p2,sources2);
        recordCollection.add(r1);
        recordCollection.add(r2);
        return recordCollection;
    }

}
