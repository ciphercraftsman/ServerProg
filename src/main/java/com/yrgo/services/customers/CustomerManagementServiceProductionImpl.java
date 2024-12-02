package com.yrgo.services.customers;

import com.yrgo.dataaccess.CustomerDao;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.dataaccess.RecordNotFoundException;

import java.util.List;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService {
    private CustomerDao customerDao;

    public CustomerManagementServiceProductionImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerDao.createCustomer(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        customerDao.updateCustomer(changedCustomer);
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        customerDao.deleteCustomer(oldCustomer.getCustomerId());
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try {
            return customerDao.getCustomerById(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found: " + customerId, e);
        }
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerDao.getCustomersByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        try {
            // Använd getFullCustomerDetail direkt
            return customerDao.getFullCustomerDetail(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found: " + customerId, e);
        }
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            // Hämta fullständig kunddetalj
            Customer customer = customerDao.getFullCustomerDetail(customerId);

            // Lägg till samtal i kunden och uppdatera databasen
            customer.getCalls().add(callDetails);
            customerDao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Customer not found for ID: " + customerId, e);
        }
    }
}
