package com.libra.bookshopmodel.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "Order status is required")
    private String orderStatus;

    @NotEmpty(message = "Book IDs must not be empty")
    private List<@NotNull Long> bookIds;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    @FutureOrPresent(message = "Order date cannot be in the past")
    private LocalDateTime orderDate;

    @Future(message = "Delivery date must be in the future")
    private LocalDateTime deliveryDate;

}
