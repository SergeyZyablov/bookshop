package com.libra.bookshopgateway.config.filters;

public class FilterUtility {

    public static String generateRequestId() {
        return java.util.UUID.randomUUID().toString();
    }
}
