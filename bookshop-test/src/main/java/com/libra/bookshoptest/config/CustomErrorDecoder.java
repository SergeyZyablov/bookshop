package com.libra.bookshoptest.config;

import com.libra.bookshopmodel.exceptions.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        // Обработка ошибок в зависимости от статуса
        if (status == HttpStatus.NOT_FOUND) {
            return new NotFoundException("Book not found in BOOKS service.");
        } else if (status == HttpStatus.SERVICE_UNAVAILABLE) {
            return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "BOOKS service is unavailable");
        } else {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred while communicating with BOOKS service");
        }
    }
}
