package com.patika.converter;

import com.patika.dto.request.CustomerSaveRequest;
import com.patika.dto.response.CustomerResponse;
import com.patika.model.Customer;
import com.patika.model.enums.AccountType;
import com.patika.util.HashUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConverter {

    public static Customer toCustomer(CustomerSaveRequest request) {
        String hashedPassword = HashUtil.generate(request.getPassword());

        Customer customer = new Customer(request.getName(), request.getSurname(), request.getEmail(), hashedPassword);
        customer.setAccountType(AccountType.STANDARD);
        customer.setIsActive(Boolean.TRUE);
        return customer;
    }

    public static CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .credit(customer.getCredit())
                .isActive(customer.getIsActive())
                .accountType(customer.getAccountType())
                .addresses(customer.getAddresses())
                .age(customer.getAge())
                .build();
    }
}
