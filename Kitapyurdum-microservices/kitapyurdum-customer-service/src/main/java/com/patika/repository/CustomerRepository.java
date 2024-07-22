package com.patika.repository;

import com.patika.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {
    private List<Customer> customerList = new ArrayList<>();

    public void save(Customer customer) {
        customerList.add(customer);
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public Optional<Customer> findByEmail(String email) {
        return customerList.stream().filter(customer -> customer.getEmail().equals(email)).findFirst();
    }

    public Optional<Customer> findById(Long id) {
        return customerList.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }
}
