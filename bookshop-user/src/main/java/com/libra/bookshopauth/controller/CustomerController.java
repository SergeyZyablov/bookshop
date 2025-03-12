package com.libra.bookshopauth.controller;

import com.libra.bookshopauth.services.CustomerService;
import com.libra.bookshopmodel.dto.customer.CustomerCreateRequest;
import com.libra.bookshopmodel.dto.customer.CustomerDto;
import com.libra.bookshopmodel.dto.customer.CustomerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/email/{email}")
    public CustomerDto getCustomerByEmail(@PathVariable("email") String email) {
        return customerService.findCustomerByEmail(email);
    }

    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        return customerService.createCustomer(customerCreateRequest);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return customerService.updateCustomer(id, customerUpdateRequest);
    }

    @PutMapping("/password/{id}")
    public String updatePassword(@PathVariable("id") Long id, @Valid @RequestBody String password) {
        return customerService.updatePassword(id, password);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        return customerService.deleteCustomer(id);
    }
}
