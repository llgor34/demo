package com.demo_app.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyValueNBPRate {
    private String currency;
    private String code;
    private double mid;
}
