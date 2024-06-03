package com.demo_app.demo.service;

import com.demo_app.demo.exception.NotFoundException;
import com.demo_app.demo.model.CurrencyValue;
import com.demo_app.demo.model.CurrencyValueNBPObject;
import com.demo_app.demo.model.CurrencyValueNBPRate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NBPCurrencyService {

    public CurrencyValue getNBPCurrencyValueByCode(String code) {
        CurrencyValueNBPRate currencyValueNBPRate = getNBPCurrencyByCode(code);
        return getCurrencyValue(currencyValueNBPRate.getMid());
    }

    public CurrencyValueNBPRate getNBPCurrencyByCode(String code) throws NotFoundException {
        List<CurrencyValueNBPRate> currencies = getNBPCurrencies().stream().filter((obj) -> obj.getCode().equals(code)).collect(Collectors.toList());
        if (currencies.isEmpty()) {
            throw new NotFoundException();
        }

        return currencies.get(0);
    }

    public List<CurrencyValueNBPRate> getNBPCurrencies() {
        return getNBPCurrencies("https://api.nbp.pl/api/exchangerates/tables/A?format=json");
    }

    public List<CurrencyValueNBPRate> getNBPCurrencies(String baseUrl) {
        WebClient client = WebClient.create();
        WebClient.ResponseSpec responseSpec = client.get().uri(baseUrl).retrieve();
        CurrencyValueNBPObject[] response = responseSpec.bodyToMono(CurrencyValueNBPObject[].class).block();

        CurrencyValueNBPRate[] currencies = response[0].getRates();
        return Arrays.stream(currencies).toList();
    }

    private CurrencyValue getCurrencyValue(double value) {
        var currencyValue = new CurrencyValue();
        currencyValue.setValue(value);
        return currencyValue;
    }
}
