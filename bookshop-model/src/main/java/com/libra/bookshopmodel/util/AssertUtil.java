package com.libra.bookshopmodel.util;

import com.libra.bookshopmodel.exceptions.NotFoundException;

import java.util.Optional;

public class AssertUtil {

    public static <T> T notNull(Optional<T> object, String errorMessage) {
        if (object.isEmpty()) {
            throw new NotFoundException(errorMessage);
        }
        return object.get();
    }
}
