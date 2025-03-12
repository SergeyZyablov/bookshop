package com.libra.bookshopmodel.dto.authority;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

    private Long id;

    private String name;

    private Long customerId;
}
