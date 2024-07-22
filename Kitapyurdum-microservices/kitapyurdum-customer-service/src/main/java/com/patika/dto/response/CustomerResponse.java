package com.patika.dto.response;

import com.patika.model.Address;
import com.patika.model.enums.AccountType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Integer credit;
    private Boolean isActive;
    private AccountType accountType;
    private Set<Address> addresses;
    private int age;
}
