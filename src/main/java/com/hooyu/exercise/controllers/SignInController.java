package com.hooyu.exercise.controllers;

import com.hooyu.exercise.SearchRequest;
import com.hooyu.exercise.customers.ChargingService;
import com.hooyu.exercise.customers.CustomerService;
import com.hooyu.exercise.customers.dao.CustomerNotFoundException;
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomerCreditsImpl;
import com.hooyu.exercise.customers.domain.Customer;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.LinkedList;

@Controller
public class SignInController {

    @Autowired
    private CustomerService customer_details;
    private SearchController searchController;
    private SearchRequest searchRequest;
    private SimpleSurnameAndPostcodeQuery query;
    private ChargingService chargingService;
    private SearchEngineRetrievalService retrievalService;
    HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits;

    Customer cust;
    HttpSession session;
    int creditsBalance =0;
    int numberOfRecordsRetireved=0;
    Collection<Record> records;
    Record record;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String signin() {

        return "signin";
    }

    @RequestMapping(value = "/signin",method = RequestMethod.GET)
    public String signin_success(@RequestParam String email, HttpSession session)
    {
      this.session=session;
      System.out.print("session is:" +session);
        if( email ==  null)
        {
            throw new CustomerNotFoundException("Invalid customer");

        }
        //Customer customer = customer_details.findCustomerByEmailAddress(email);
        cust = customer_details.findCustomerByEmailAddress(email);
        if( cust != null)
        {
            session.setAttribute("loggedInUser", cust);
            return "search.html";
        }
       return null;
    }

    @RequestMapping(value = "/searchDetails",  method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView searchDetails(@RequestParam String surname, @RequestParam String postcode) throws NullPointerException
    {
        if(session!=null)
        {
          cust = (Customer) session.getAttribute("loggedInUser");
        }
        else
            throw new NullPointerException();
        records = processFetchedDetailsFromURI(surname,postcode);
        ModelAndView model = new ModelAndView();
        model.setViewName("customerdetails");
        model.addObject("records",records);
        creditsBalance=hardcodedListOfCustomerCredits.getCustomerCredits(cust.getEmailAddress());
        model.addObject("creditsavailable",creditsBalance);
        return model;

    }


    @RequestMapping(value = "/searchDetailsjson",  method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public  Collection<Record> searchDetails_json_output(@RequestParam String surname, @RequestParam String postcode, Model model)
    {
        records = processFetchedDetailsFromURI(surname,postcode);
        return records;
    }

    public Collection<Record> processFetchedDetailsFromURI(String surname, String postcode)
    {
        //System.out.print("in controller surname entered is " + surname +"Postcode is " +postcode);
        query = new SimpleSurnameAndPostcodeQuery(surname,postcode);
        //System.out.print("query is:: "+query);
        cust = (Customer) session.getAttribute("loggedInUser");
        //System.out.print("Session customer is***********;" +cust.getSurname());
        Collection<Record> records = processRecordsforCredits(query,cust);
        return  records;
    }

    public Collection<Record> processRecordsforCredits(SimpleSurnameAndPostcodeQuery query, Customer cust)
    {
    searchRequest = new SearchRequest(query,cust);
    chargingService=new ChargingService();
    Collection<Record> records = searchRequest.search(query);
    Collection<Record> bt_records = new LinkedList<Record>();
        hardcodedListOfCustomerCredits = new HardcodedListOfCustomerCreditsImpl();
    /*ModelAndView model = new ModelAndView();
    model.setViewName("customerdetails");*/
    if(cust.getCustomType().toString().contains("PREMIUM"))
    {
        int bt_records_size=0;
        //Returning all Records
        //model.addObject("records",records);
        for (Record r:records
                ) {
            if( r.getSourceTypes().toString().contains("BT"))
            {
                bt_records_size++;
            }
        }
        //System.out.print("number of BT records for the customer is :%%%%" +bt_records_size);
        numberOfRecordsRetireved=records.size()-bt_records_size;
        chargingService.charge(cust.getEmailAddress(),numberOfRecordsRetireved);
        //creditsBalance=hardcodedListOfCustomerCredits.getCustomerCredits(cust.getEmailAddress());
        //System.out.print("In the last pahse, credits available are;" +hardcodedListOfCustomerCredits.getCustomerCredits(cust.getEmailAddress()));
        return records;
    }
    else
    {
        //Returning BT Specific Records
        //System.out.print(" he is non paying customer");
        for (Record r:records
                ) {
            if( r.getSourceTypes().toString().contains("BT"))
            {
                bt_records.add(r);
                //model.addObject("records",bt_records);
                return bt_records;
            }

        }

    }


    return null;

}
}
