package com.libra.bookshopmodel.entity;

import com.libra.bookshopmodel.dto.order.OrderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookshop_order")
public class OrderEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookEntity> bookEntities;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    public OrderDto toDto() {
        return new OrderDto(
                this.id,
                this.orderStatus != null ? this.orderStatus.name() : null, // Преобразуем Enum в строку
                this.bookEntities != null
                        ? this.bookEntities.stream().map(BookEntity::getId).collect(Collectors.toList())
                        : null, // Извлекаем только id книг
                this.totalPrice,
                this.customer != null ? this.customer.getId() : null, // Извлекаем id клиента
                this.shippingAddress,
                this.paymentMethod != null ? this.paymentMethod.name() : null, // Преобразуем Enum в строку
                this.orderDate,
                this.deliveryDate
        );
    }
}
