package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS (ID, NAME, NOTES) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getCustomerId(), customer.getCompanyName(), customer.getNotes());
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE CUSTOMERS SET NAME = ?, NOTES = ? WHERE ID = ?";
        jdbcTemplate.update(sql, customer.getCompanyName(), customer.getNotes(), customer.getCustomerId());
    }

    @Override
    public void deleteCustomer(String customerId) {
        String sql = "DELETE FROM CUSTOMERS WHERE ID = ?";
        jdbcTemplate.update(sql, customerId);
    }

    @Override
    public Customer getCustomerById(String customerId) throws RecordNotFoundException {
        String sql = "SELECT * FROM CUSTOMERS WHERE ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapCustomer, customerId);
        } catch (Exception e) {
            throw new RecordNotFoundException("Customer not found: " + customerId, e);
        }
    }

    @Override
    public List<Customer> getCustomersByName(String name) {
        String sql = "SELECT * FROM CUSTOMERS WHERE NAME = ?";
        return jdbcTemplate.query(sql, this::mapCustomer, name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM CUSTOMERS";
        return jdbcTemplate.query(sql, this::mapCustomer);
    }

    @Override
    public List<Call> getCallsForCustomer(String customerId) {
        String sql = "SELECT * FROM CALLS WHERE CUSTOMER_ID = ?";
        return jdbcTemplate.query(sql, this::mapCall, customerId);
    }

    @Override
    public void addCallForCustomer(String customerId, Call call) {
        String sql = "INSERT INTO CALLS (CUSTOMER_ID, TIME_AND_DATE, NOTES) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customerId, call.getTimeAndDate(), call.getNotes());
    }

    private Customer mapCustomer(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getString("ID"));
        customer.setCompanyName(rs.getString("NAME"));
        customer.setNotes(rs.getString("NOTES"));
        return customer;
    }

    private Call mapCall(ResultSet rs, int rowNum) throws SQLException {
        Call call = new Call();
        call.setTimeAndDate(rs.getTimestamp("TIME_AND_DATE"));
        call.setNotes(rs.getString("NOTES"));
        return call;
    }

    @Override
    public void createTables() {
        // Kontrollera om tabellen CUSTOMERS redan finns
        jdbcTemplate.execute("CREATE TABLE CUSTOMERS (ID VARCHAR(50) PRIMARY KEY, NAME VARCHAR(100), NOTES VARCHAR(255))");

        // Kontrollera om tabellen CALLS redan finns
        jdbcTemplate.execute("CREATE TABLE CALLS (ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, CUSTOMER_ID VARCHAR(50), TIME_AND_DATE TIMESTAMP, NOTES VARCHAR(255))");
    }


}
