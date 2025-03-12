package com.libra.bookshopmodel.dto.customer;

import com.libra.bookshopmodel.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
