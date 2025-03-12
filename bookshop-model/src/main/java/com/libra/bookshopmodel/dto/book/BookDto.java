package com.libra.bookshopmodel.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private String authorName;
    private String genreName;
}
