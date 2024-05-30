package com.demo_app.demo.model;

import lombok.Data;

@Data
public class CurrencyValueNBPRate {
    private String currency;
    private String code;
    private double mid;
}
