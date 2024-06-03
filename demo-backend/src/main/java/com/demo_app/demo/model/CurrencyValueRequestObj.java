package com.demo_app.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyValueRequestObj {
    @NotBlank(message = "Field 'currency' is required!")
    private String currency;

    @NotBlank(message = "Field 'name' is required!")
    private String name;
}
