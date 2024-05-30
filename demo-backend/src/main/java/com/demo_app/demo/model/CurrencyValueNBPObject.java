package com.demo_app.demo.model;

import lombok.Data;


@Data
public class CurrencyValueNBPObject {
    private String table;
    private String no;
    private String effectiveDate;
    private CurrencyValueNBPRate[] rates;
}
