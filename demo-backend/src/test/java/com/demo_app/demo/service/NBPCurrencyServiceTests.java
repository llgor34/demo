package com.demo_app.demo.service;

import com.demo_app.demo.exception.NotFoundException;
import com.demo_app.demo.model.CurrencyValue;
import com.demo_app.demo.model.CurrencyValueNBPRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class NBPCurrencyServiceTests {


    @InjectMocks
    @Spy
    NBPCurrencyService nbpCurrencyService;

    @Test
    public void getNBPCurrencyValueByCodeShouldCallgetNBPCurrencyByCode() {
        Mockito.doReturn(new CurrencyValueNBPRate()).when(nbpCurrencyService).getNBPCurrencyByCode("");
        nbpCurrencyService.getNBPCurrencyValueByCode("");
        Mockito.verify(nbpCurrencyService).getNBPCurrencyByCode("");
    }

    @Test
    public void getNBPCurrencyValueByCodeShouldReturnCorrectValue() {
        CurrencyValueNBPRate currencyValueNBPRate = new CurrencyValueNBPRate("", "", 12.5);
        Mockito.doReturn(currencyValueNBPRate).when(nbpCurrencyService).getNBPCurrencyByCode("");

        CurrencyValue actualValue = nbpCurrencyService.getNBPCurrencyValueByCode("");
        CurrencyValue expectedValue = new CurrencyValue(12.5);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void getNBPCurrencyByCodeShouldCallgetNBPCurrencies() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = new ArrayList<>();
        CurrencyValueNBPRate currencyValueNBPRate = new CurrencyValueNBPRate("", "", 12.5);
        currencyValueNBPRates.add(currencyValueNBPRate);
        Mockito.doReturn(currencyValueNBPRates).when(nbpCurrencyService).getNBPCurrencies();

        nbpCurrencyService.getNBPCurrencyByCode("");

        Mockito.verify(nbpCurrencyService).getNBPCurrencies();
    }

    @Test
    public void getNBPCurrencyByCodeShouldCallReturnCorrectValue() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = new ArrayList<>();
        CurrencyValueNBPRate currencyValueNBPRate1 = new CurrencyValueNBPRate("Dollar", "USD", 12.5);
        CurrencyValueNBPRate currencyValueNBPRate2 = new CurrencyValueNBPRate("Juan", "JU", 30);
        currencyValueNBPRates.add(currencyValueNBPRate1);
        currencyValueNBPRates.add(currencyValueNBPRate2);
        Mockito.doReturn(currencyValueNBPRates).when(nbpCurrencyService).getNBPCurrencies();

        CurrencyValueNBPRate actualCurrencyValueNBPRate = nbpCurrencyService.getNBPCurrencyByCode("USD");

        assertEquals(currencyValueNBPRate1, actualCurrencyValueNBPRate);
    }

    @Test
    public void getNBPCurrencyByCodeShouldThrowNotFoundExceptionWhenListIsEmpty() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = new ArrayList<>();
        Mockito.doReturn(currencyValueNBPRates).when(nbpCurrencyService).getNBPCurrencies();

        try {
            nbpCurrencyService.getNBPCurrencyByCode("");
            fail("NotFound exception was not throwed!");
        } catch(NotFoundException e) {}
    }
}
