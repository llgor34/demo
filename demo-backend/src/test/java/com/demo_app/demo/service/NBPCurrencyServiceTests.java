package com.demo_app.demo.service;

import com.demo_app.demo.model.CurrencyValueNBPRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NBPCurrencyServiceTests {

    @InjectMocks
    NBPCurrencyService nbpCurrencyService;

    @Test
    public void getNBPCurrencyValueByCodeShouldCallgetNBPCurrencyByCode() {
        nbpCurrencyService.getNBPCurrencyValueByCode("");
        Mockito.verify(nbpCurrencyService).getNBPCurrencyByCode("");
    }
}
