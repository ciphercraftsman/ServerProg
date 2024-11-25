package com.yrgo.dataaccess;

import java.util.List;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public interface CustomerDao {

	void createCustomer(Customer customer);

	Customer getCustomerById(String customerId) throws RecordNotFoundException;

	List<Customer> getCustomersByName(String name);

	void updateCustomer(Customer customerToUpdate);

	void deleteCustomer(String customerId);

	List<Customer> getAllCustomers();

	List<Call> getCallsForCustomer(String customerId);

	void addCallForCustomer(String customerId, Call newCall);

	void createTables();
}

