package com.libra.bookshopmodel.entity;

public enum OrderStatus {
    NEW,               // Новый заказ, только что создан
    PROCESSING,        // Заказ обрабатывается
    AWAITING_PAYMENT,  // Ожидается оплата
    PAID,              // Заказ оплачен
    SHIPPED,           // Заказ отправлен
    DELIVERED,         // Заказ доставлен клиенту
    CANCELLED,         // Заказ отменен
    RETURNED           // Товар возвращен
}
