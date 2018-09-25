package com.hooyu.exercise.controllers

import com.hooyu.exercise.customers.CustomerService
import com.hooyu.exercise.customers.dao.CustomerNotFoundException
import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import org.powermock.api.easymock.PowerMock
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.springframework.mock.web.MockHttpSession
import org.junit.runner.*;
import spock.lang.Specification

@PrepareForTest(value = CustomerService.class)
class SigninControllerTest extends Specification {

    SignInController controller;
    SimpleSurnameAndPostcodeQuery query;
    MockHttpSession mockHttpSession;
    private String joe_email="john.doe@192.com";

    def setup() {
        controller = new SignInController();
        query = new SimpleSurnameAndPostcodeQuery("Smith", "sw6 2bq");
        mockHttpSession = new MockHttpSession();
    }


    def "return valid html when the sign in successful"() {

        when: "I do a successful sign in"
        SignInController signInController = new SignInController();
        CustomerService spyCustomerService = PowerMockito.mock(CustomerService.class)
        PowerMockito.whenNew(CustomerService.class).withNoArguments().thenReturn(spyCustomerService);
        PowerMockito.when(spyCustomerService.findCustomerByEmailAddress(asType(String.class))).thenReturn(getExpectedCustomer())
        String returnValue = signInController.signin_success(joe_email, mockHttpSession)

        then: "i should be able to view correct html page"
        assert returnValue.equals("search.html");
    }


    def "throw exception for an invalid email id sign in attempt"() {

        when: "return valid html when the sign in sucessful"
        SignInController signInController = new SignInController();
        String invalid_email="xyz@abc.com";

        and:
        signInController.signin_success(invalid_email, mockHttpSession)

        then:
        thrown CustomerNotFoundException
    }



    def "Find records for provided Surname and Postcode"() {

        given: "I have record details for Surname - Smith and postcode - sw6 2bq"
        String surname= "Smith";
        String postCode = "sw6 2bq"

        SimpleSurnameAndPostcodeQuery simpleSurnameAndPostcodeQuery = new SimpleSurnameAndPostcodeQuery(surname,postCode);
        Customer customer = getExpectedCustomer();

        when:
        Collection<Record> collection = controller.processRecordsforCredits(simpleSurnameAndPostcodeQuery,customer);

        then:
        assert collection.toArray().sort()[0].equals(getExpectedRecords().toArray().sort()[0])
    }


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
