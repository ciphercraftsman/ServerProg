package com.yrgo.client;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SimpleClient {
    public static void main(String[] args) {
        // Load Spring context from application.xml
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        // Get the CustomerManagementService bean
        CustomerManagementService customerService = (CustomerManagementService) context.getBean("customerManagementService");

        // Example: Add a new customer
        Customer newCustomer = new Customer("MJ45", "Mars Ltd", "Initial notes");
        customerService.newCustomer(newCustomer);

        // Retrieve and print all customers
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Customer Company: " + customer.getCompanyName());
            System.out.println("Customer Notes: " + customer.getNotes());
            System.out.println();
        }
    }
}
