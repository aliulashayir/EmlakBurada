package com.patika.dto.request;

import com.patika.model.Address;
import com.patika.model.enums.AccountType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateRequest {
    private String email;
    private String newEmail;
    private Set<Address> addresses;
    private AccountType accountType;
}
