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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.LinkedList;

@Controller
public class SignInController {

    HardcodedListOfCustomerCreditsImpl hardcodedListOfCustomerCredits;
    int creditsBalance = 0;
    int numberOfRecordsRetireved = 0;
    Collection<Record> records;
    Record record;
    private CustomerService customer_details = new CustomerService();
    private SearchController searchController;
    private SearchRequest searchRequest;
    private SimpleSurnameAndPostcodeQuery query;
    private ChargingService chargingService;
    private SearchEngineRetrievalService retrievalService;
    private Customer cust;
    private HttpSession session;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String signin() {
        return "signin";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin_success(@RequestParam String email, HttpSession session) {
        this.session = session;
        System.out.print("session is:" + session);
        if (email == null) {
            throw new CustomerNotFoundException("Invalid customer");

        }
        cust = customer_details.findCustomerByEmailAddress(email);
        if (cust != null) {
            session.setAttribute("loggedInUser", cust);
            return "search.html";
        }
        return null;
    }

    @RequestMapping(value = "/searchDetails", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView searchDetails(@RequestParam String surname, @RequestParam String postcode) {
        if (session != null) {
            cust = (Customer) session.getAttribute("loggedInUser");
        } else
            throw new NullPointerException();
        records = fetchedDetailsFromURI(surname, postcode);
        ModelAndView model = new ModelAndView();
        model.setViewName("customerdetails");
        model.addObject("records", records);
        creditsBalance = hardcodedListOfCustomerCredits.getCustomerCredits(cust.getEmailAddress());
        model.addObject("creditsavailable", creditsBalance);
        return model;
    }

    @RequestMapping(value = "/searchDetailsjson", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Collection<Record> searchDetails_json_output(@RequestParam String surname, @RequestParam String postcode, Model model) {
        records = fetchedDetailsFromURI(surname, postcode);
        return records;
    }

    public Collection<Record> fetchedDetailsFromURI(String surname, String postcode) {
        query = new SimpleSurnameAndPostcodeQuery(surname, postcode);
        cust = (Customer) session.getAttribute("loggedInUser");
        Collection<Record> records = processRecordsforCredits(query, cust);
        return records;
    }

    public Collection<Record> processRecordsforCredits(SimpleSurnameAndPostcodeQuery query, Customer cust) {
        int btRecordsSize = 0;
        searchRequest = new SearchRequest(query, cust);
        chargingService = new ChargingService();
        Collection<Record> records = searchRequest.search(query);
        Collection<Record> bt_records = new LinkedList<Record>();
        hardcodedListOfCustomerCredits = new HardcodedListOfCustomerCreditsImpl();

        if (cust.getCustomType().toString().contains("PREMIUM")) {
            btRecordsSize = 0;
            for (Record r : records) {
                if (r.getSourceTypes().toString().contains("BT")) {
                    btRecordsSize++;
                }
            }
            numberOfRecordsRetireved = records.size() - btRecordsSize;
            chargingService.charge(cust.getEmailAddress(), numberOfRecordsRetireved);
            return records;
        } else {
            for (Record r : records
            ) {
                if (r.getSourceTypes().toString().contains("BT")) {
                    bt_records.add(r);
                    return bt_records;
                }
            }
        }
        return null;
    }
}
