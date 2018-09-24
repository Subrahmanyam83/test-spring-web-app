package com.hooyu.exercise.customers;

import com.hooyu.exercise.customers.dao.CustomerDao;
import com.hooyu.exercise.customers.dao.CustomerNotFoundException;
import com.hooyu.exercise.customers.dao.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Subrahmanyam on 21/09/2018.
 */

@Service
public class CustomerService extends HardcodedListOfCustomersImpl {


   // private HardcodedListOfCustomersImpl customersList = new HardcodedListOfCustomersImpl();



}
