package com.libra.bookshopmodel.dto.customer;

import com.libra.bookshopmodel.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotNull
    private Role role;
}
