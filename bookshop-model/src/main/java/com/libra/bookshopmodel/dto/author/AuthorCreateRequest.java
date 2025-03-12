package com.libra.bookshopmodel.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorCreateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private List<@NotNull Long> bookIds;
}
