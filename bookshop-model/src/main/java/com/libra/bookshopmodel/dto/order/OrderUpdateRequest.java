package com.libra.bookshopmodel.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateRequest {

    private String orderStatus;

    private List<@NotNull Long> bookIds;

    private String shippingAddress;

    private String paymentMethod;

    @FutureOrPresent(message = "Order date cannot be in the past")
    private LocalDateTime orderDate;

    @Future(message = "Delivery date must be in the future")
    private LocalDateTime deliveryDate;
}
