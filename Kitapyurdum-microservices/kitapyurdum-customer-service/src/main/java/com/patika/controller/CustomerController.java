package com.patika.controller;
import com.patika.converter.CustomerConverter;
import com.patika.dto.request.CustomerSaveRequest;
import com.patika.dto.request.CustomerUpdateRequest;
import com.patika.dto.response.CustomerResponse;
import com.patika.dto.response.ProductResponse;
import com.patika.model.Customer;
import com.patika.model.enums.AccountType;
import com.patika.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CustomerSaveRequest request) {
        customerService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getCustomerList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        Customer customer = customerService.getById(id);
        return ResponseEntity.ok(CustomerConverter.toResponse(customer));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponse> getByEmail(@PathVariable String email) {
        Customer customer = customerService.getByEmail(email);
        return ResponseEntity.ok(CustomerConverter.toResponse(customer));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllCustomers() {
        long count = customerService.countAllCustomers();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/products-by-name")
    public ResponseEntity<List<ProductResponse>> findProductsByCustomerName(@RequestParam String name) {
        List<ProductResponse> products = customerService.findProductsByCustomerName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/total-amount-by-name-and-age")
    public ResponseEntity<BigDecimal> calculateTotalAmountByCustomerNameAndAge(@RequestParam String name, @RequestParam int minAge, @RequestParam int maxAge) {
        BigDecimal totalAmount = customerService.calculateTotalAmountByCustomerNameAndAge(name, minAge, maxAge);
        return ResponseEntity.ok(totalAmount);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerUpdateRequest request) {
        customerService.updateCustomerInfo(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-account-type")
    public ResponseEntity<Void> updateAccountType(@RequestParam String email, @RequestParam AccountType accountType) {
        customerService.changeAccountType(email, accountType);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
