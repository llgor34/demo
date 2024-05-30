package com.demo_app.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCurrencyAccessDTO {

    private String currency;
    private String name;
    private Timestamp date;
    private double value;

}
