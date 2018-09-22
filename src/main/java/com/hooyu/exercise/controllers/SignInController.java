package com.hooyu.exercise.controllers;

import com.hooyu.exercise.SearchRequest;
import com.hooyu.exercise.customers.CustomerService;
import com.hooyu.exercise.customers.dao.CustomerDao;
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class SignInController {

    @Autowired
    private CustomerService customer_details;


    private SearchRequest searchRequest;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String signin() {

        return "signin";
    }

    @RequestMapping(value = "/signin",method = RequestMethod.GET)
    public String signin_success(@RequestParam String email, HttpSession session)
    {

        if( email ==  null)
        {
            //Handle Exception here
            return null;
        }
        Customer customer = customer_details.findCustomerByEmailAddress(email);
        if( customer != null)
        {
           session.setAttribute("loggedInUser", customer);
           //return "redirect:/search.html";
            return  "redirect:/search.html";


        }

       return "hii9";
    }
/*
    @RequestMapping(value = "/searchDetails" ,method = RequestMethod.POST)
    public String searchDetails(@RequestParam String surname, @RequestParam String postCode)
    {
        System.out.print("in controller surname entered is" + surname);
      Customer cutomer = searchRequest.searchCustomerDetailsFromSearchForm(surname);
        System.out.print(cutomer.getCustomType()+" "+cutomer.getEmailAddress());
        return "search";
    }
*/}
