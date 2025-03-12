package com.libra.bookshoporder.services;

import com.libra.bookshopdata.repository.BookRepository;
import com.libra.bookshopdata.repository.CustomerRepository;
import com.libra.bookshopdata.repository.OrderRepository;
import com.libra.bookshopmodel.dto.order.OrderCreateRequest;
import com.libra.bookshopmodel.dto.order.OrderDto;
import com.libra.bookshopmodel.dto.order.OrderUpdateRequest;
import com.libra.bookshopmodel.entity.*;
import com.libra.bookshopmodel.exceptions.NotFoundException;
import com.libra.bookshopmodel.util.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    public static final String ORDER_NOT_FOUND_WITH_ID = "Order not found with id: ";

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<OrderDto> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderEntity::toDto).toList();
    }

    @Transactional(readOnly = true)
    public OrderDto findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderEntity::toDto)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_WITH_ID + id));
    }

    @Transactional
    public OrderDto createOrder(OrderCreateRequest request) {
        // Создаем новый заказ
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));

        orderEntity.setShippingAddress(request.getShippingAddress());
        orderEntity.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        orderEntity.setOrderDate(request.getOrderDate());
        orderEntity.setDeliveryDate(request.getDeliveryDate());

        CustomerEntity customerEntity = AssertUtil.notNull(customerRepository.findById(request.getCustomerId()),
                String.format("Customer with id %d not found.", request.getCustomerId()));
        orderEntity.setCustomer(customerEntity);
        List<BookEntity> books = getBooks(request.getBookIds());
        orderEntity.setBookEntities(books);
        BigDecimal totalPrice = books.stream().map(BookEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderEntity.setTotalPrice(totalPrice);

        // Сохраняем заказ
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return savedOrder.toDto();
    }

    @Transactional
    public OrderDto updateOrder(Long orderId, OrderUpdateRequest request) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_WITH_ID + orderId));

        if (request.getOrderStatus() != null) {
            orderEntity.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));
        }

        if (request.getShippingAddress() != null) {
            orderEntity.setShippingAddress(request.getShippingAddress());
        }
        if (request.getPaymentMethod() != null) {
            orderEntity.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        }
        if (request.getOrderDate() != null) {
            orderEntity.setOrderDate(request.getOrderDate());
        }
        if (request.getDeliveryDate() != null) {
            orderEntity.setDeliveryDate(request.getDeliveryDate());
        }
        if (request.getBookIds() != null) {
            List<BookEntity> books = getBooks(request.getBookIds());
            orderEntity.setBookEntities(books);
            BigDecimal totalPrice = books.stream().map(BookEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderEntity.setTotalPrice(totalPrice);
        }

        OrderEntity updatedOrder = orderRepository.save(orderEntity);
        return updatedOrder.toDto();
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException(ORDER_NOT_FOUND_WITH_ID + id);
        }
        orderRepository.deleteById(id);
    }

    private List<BookEntity> getBooks(List<Long> bookIds) {
        if (bookIds == null || bookIds.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> bookIdsSet = new HashSet<>(bookIds);
        return bookIdsSet.stream().map(bookId ->
                        AssertUtil.notNull(bookRepository.findById(bookId),
                                String.format("Book with id %d not found.", bookId)))
                .toList();
    }
}
