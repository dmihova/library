package com.tinqin.library.book.rest.config;

public class WhitelistedEndpoints {
    public static String[] WHITELISTED_ENDPOINTS = {
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/v3/api-docs*/**", /// needed for swagger
            "/api/v1/**",

            "/actuator/**",
            "/instances/**",
            "/v3/**",
            "/error"

    };
}
