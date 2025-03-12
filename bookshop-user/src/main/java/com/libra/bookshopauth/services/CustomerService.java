package com.libra.bookshopauth.services;

import com.libra.bookshopdata.repository.AuthorityRepository;
import com.libra.bookshopdata.repository.CustomerRepository;
import com.libra.bookshopmodel.dto.customer.CustomerCreateRequest;
import com.libra.bookshopmodel.dto.customer.CustomerDto;
import com.libra.bookshopmodel.dto.customer.CustomerUpdateRequest;
import com.libra.bookshopmodel.entity.AuthorityEntity;
import com.libra.bookshopmodel.entity.CustomerEntity;
import com.libra.bookshopmodel.util.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public static final String CUSTOMER_WITH_ID_D_NOT_FOUND = "Customer with id: %d not found";
    public static final String CUSTOMER_WITH_EMAIL_S_NOT_FOUND = "Customer with email: %s not found";

    private final CustomerRepository customerRepository;
    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public CustomerDto findCustomerById(Long id) {
        CustomerEntity customerEntity = AssertUtil.notNull(customerRepository.findById(id),
                String.format(CUSTOMER_WITH_ID_D_NOT_FOUND, id));
        return customerEntity.toDto();
    }

    @Transactional(readOnly = true)
    public CustomerDto findCustomerByEmail(String email) {
        CustomerEntity customerEntity = AssertUtil.notNull(customerRepository.findByEmail(email),
                String.format(CUSTOMER_WITH_EMAIL_S_NOT_FOUND, email));
        return customerEntity.toDto();
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> findAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerEntity::toDto).toList();
    }

    @Transactional
    public CustomerDto createCustomer(CustomerCreateRequest customerCreateRequest) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerCreateRequest.getName());
        customerEntity.setEmail(customerCreateRequest.getEmail());

        String encodedPassword = passwordEncoder.encode(customerCreateRequest.getPassword());
        customerEntity.setPassword(encodedPassword);
        customerEntity.setRole(customerCreateRequest.getRole());
        customerEntity.setAuthorities(List.of());
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        AuthorityEntity authority = new AuthorityEntity();
        authority.setCustomer(savedCustomer);
        authority.setName("USER");
        authorityRepository.save(authority);

        return savedCustomer.toDto();
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerUpdateRequest customerUpdateRequest) {
        CustomerEntity customerEntity = AssertUtil.notNull(customerRepository.findById(id),
                String.format(CUSTOMER_WITH_ID_D_NOT_FOUND, id));

        customerEntity.setName(customerUpdateRequest.getName());
        customerEntity.setEmail(customerUpdateRequest.getEmail());
        customerEntity.setRole(customerUpdateRequest.getRole());
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);
        return updatedCustomer.toDto();
    }

    @Transactional
    public String updatePassword(Long id, String password) {
        CustomerEntity customerEntity = AssertUtil.notNull(customerRepository.findById(id),
                String.format(CUSTOMER_WITH_ID_D_NOT_FOUND, id));
        String encodedPassword = passwordEncoder.encode(password);
        customerEntity.setPassword(encodedPassword);
        customerRepository.save(customerEntity);
        return "Password updated";
    }

    @Transactional
    public String deleteCustomer(Long id) {
        CustomerEntity customerEntity = AssertUtil.notNull(customerRepository.findById(id),
                String.format(CUSTOMER_WITH_ID_D_NOT_FOUND, id));

        customerRepository.delete(customerEntity);

        return String.format("Customer with id: %d deleted", id);
    }

}
