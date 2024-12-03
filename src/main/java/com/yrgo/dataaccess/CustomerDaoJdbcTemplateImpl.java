package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createCustomer(Customer customer) {
        String checkSql = "SELECT COUNT(*) FROM CUSTOMERS WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, customer.getCustomerId());

        if (count != null && count > 0) {
            System.out.println("Customer with ID " + customer.getCustomerId() + " already exists. Skipping insert.");
            return;
        }

        String insertSql = "INSERT INTO CUSTOMERS (ID, NAME, NOTES) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, customer.getCustomerId(), customer.getCompanyName(), customer.getNotes());
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
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        String sql = "INSERT INTO CALLS (CUSTOMER_ID, TIME_AND_DATE, NOTES) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customerId, newCall.getTimeAndDate(), newCall.getNotes());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        // Hämta kunden
        Customer customer = getCustomerById(customerId);

        // Hämta tillhörande samtal
        String sql = "SELECT * FROM CALLS WHERE CUSTOMER_ID = ?";
        List<Call> calls = jdbcTemplate.query(sql, this::mapCall, customerId);

        // Koppla samtalen till kunden
        customer.setCalls(calls);
        return customer;
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

    public void createTables() {
        try {
            jdbcTemplate.execute("DROP TABLE CALLS");
        } catch (Exception e) {
            System.out.println("CALLS table does not exist, skipping DROP.");
        }

        try {
            jdbcTemplate.execute("DROP TABLE CUSTOMERS");
        } catch (Exception e) {
            System.out.println("CUSTOMERS table does not exist, skipping DROP.");
        }

        jdbcTemplate.execute("CREATE TABLE CUSTOMERS (ID VARCHAR(50) PRIMARY KEY, NAME VARCHAR(100), NOTES VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE CALLS (ID INTEGER IDENTITY PRIMARY KEY, CUSTOMER_ID VARCHAR(50), TIME_AND_DATE TIMESTAMP, NOTES VARCHAR(255))");
    }
}
