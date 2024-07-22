package com.patika.dto.request;

import com.patika.model.Address;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSaveRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private Set<Address> addresses;
}
