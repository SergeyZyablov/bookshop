package com.libra.bookshopmodel.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private Long id;

    private String name;

    private List<Long> bookIds;

}
