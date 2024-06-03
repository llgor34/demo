package com.demo_app.demo.controller;

import com.demo_app.demo.model.CurrencyValue;
import com.demo_app.demo.model.CurrencyValueRequestObj;
import com.demo_app.demo.model.UserCurrencyAccessDTO;
import com.demo_app.demo.service.UserCurrencyService;
import com.demo_app.demo.service.NBPCurrencyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    UserCurrencyService userCurrencyService;
    NBPCurrencyService NBPCurrencyService;

    @PostMapping("/get-current-currency-value-command")
    public CurrencyValue getCurrentCurrencyValue(@RequestBody @Valid CurrencyValueRequestObj currencyValueRequestObj) {
        CurrencyValue currencyValue = NBPCurrencyService.getNBPCurrencyValueByCode(currencyValueRequestObj.getCurrency());

        userCurrencyService.saveUserCurrencyAccess(
                currencyValueRequestObj.getName(),
                currencyValueRequestObj.getCurrency(),
                currencyValue.getValue()
        );

        return currencyValue;
    }

    @GetMapping("/requests")
    public List<UserCurrencyAccessDTO> getUserCurrencyAccessDTO() {
        return userCurrencyService.getAllUserCurrencyAccessDTO();
    }
}
