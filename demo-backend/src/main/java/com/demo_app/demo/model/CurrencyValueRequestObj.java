package com.demo_app.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CurrencyValueRequestObj {
    @NotBlank(message = "Field 'currency' is required!")
    private String currency;

    @NotBlank(message = "Field 'name' is required!")
    private String name;
}
