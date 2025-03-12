package com.libra.bookshopmodel.entity;

public enum PaymentMethod {
    CREDIT_CARD("Оплата кредитной картой"),
    BANK_TRANSFER("Банковский перевод"),
    CASH("Оплата наличными");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
