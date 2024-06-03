package com.demo_app.demo.service;

import com.demo_app.demo.exception.NotFoundException;
import com.demo_app.demo.model.CurrencyValue;
import com.demo_app.demo.model.CurrencyValueNBPObject;
import com.demo_app.demo.model.CurrencyValueNBPRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class NBPCurrencyServiceTests {

    @Spy
    NBPCurrencyService nbpCurrencyService;

    @Test
    public void getNBPCurrenciesShouldCallNBPAPiAndReturnRates() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MockWebServer mockWebServer = new MockWebServer();
            mockWebServer.start();

           List<CurrencyValueNBPObject> currencyValueNBPObjectList = getMockCurrencyValueNBPObjectList();

            mockWebServer.enqueue(
                    new MockResponse()
                            .setResponseCode(Response.SC_OK)
                            .setBody(objectMapper.writeValueAsString(currencyValueNBPObjectList))
                            .addHeader("Content-Type", "application/json")
            );

            String baseUrl = mockWebServer.url("/").toString();
            List<CurrencyValueNBPRate> rates = nbpCurrencyService.getNBPCurrencies(baseUrl);
            RecordedRequest recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);

            assertEquals("GET", recordedRequest.getMethod());
            assertEquals("/", recordedRequest.getPath());
            assertThat(rates.get(0)).isEqualTo(currencyValueNBPObjectList.get(0).getRates()[0]);

            mockWebServer.close();

        } catch(IOException exception) {
            fail("An IOException occured!");
        } catch(InterruptedException exception) {
            fail("An InterruptedException ocurred");
        }

    }

    @Test
    public void getNBPCurrencyValueByCodeShouldCallgetNBPCurrencyByCode() {
        Mockito.doReturn(new CurrencyValueNBPRate()).when(nbpCurrencyService).getNBPCurrencyByCode("");
        nbpCurrencyService.getNBPCurrencyValueByCode("");
        Mockito.verify(nbpCurrencyService).getNBPCurrencyByCode("");
    }

    @Test
    public void getNBPCurrencyValueByCodeShouldReturnCorrectValue() {
        CurrencyValueNBPRate currencyValueNBPRate = getMockCurrencyValueNBPRateList().get(0);
        Mockito.doReturn(currencyValueNBPRate).when(nbpCurrencyService).getNBPCurrencyByCode(currencyValueNBPRate.getCode());

        CurrencyValue actualValue = nbpCurrencyService.getNBPCurrencyValueByCode(currencyValueNBPRate.getCode());
        CurrencyValue expectedValue = new CurrencyValue(currencyValueNBPRate.getMid());

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void getNBPCurrencyByCodeShouldCallgetNBPCurrencies() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = getMockCurrencyValueNBPRateList();
        Mockito.doReturn(currencyValueNBPRates).when(nbpCurrencyService).getNBPCurrencies();

        nbpCurrencyService.getNBPCurrencyByCode(currencyValueNBPRates.get(0).getCode());
        Mockito.verify(nbpCurrencyService).getNBPCurrencies();
    }

    @Test
    public void getNBPCurrencyByCodeShouldCallReturnCorrectValue() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = getMockCurrencyValueNBPRateList();

        Mockito.doReturn(currencyValueNBPRates).when(nbpCurrencyService).getNBPCurrencies();

        CurrencyValueNBPRate actualCurrencyValueNBPRate = nbpCurrencyService.getNBPCurrencyByCode(currencyValueNBPRates.get(0).getCode());
        assertEquals(currencyValueNBPRates.get(0), actualCurrencyValueNBPRate);
    }

    @Test
    public void getNBPCurrencyByCodeShouldThrowNotFoundExceptionWhenListIsEmpty() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = new ArrayList<>();
        Mockito.doReturn(currencyValueNBPRates).when(nbpCurrencyService).getNBPCurrencies();

        try {
            nbpCurrencyService.getNBPCurrencyByCode("");
            fail("NotFound exception was not throwed!");
        } catch (NotFoundException e) {
        }
    }

    private List<CurrencyValueNBPObject> getMockCurrencyValueNBPObjectList() {
        List<CurrencyValueNBPObject> currencyValueBPObjectList = new ArrayList<>();
        CurrencyValueNBPObject currencyValueNBPObject = new CurrencyValueNBPObject();
        CurrencyValueNBPRate currencyValueNBPRate = new CurrencyValueNBPRate();

        currencyValueNBPObject.setRates(new CurrencyValueNBPRate[]{currencyValueNBPRate});
        currencyValueBPObjectList.add(currencyValueNBPObject);

        return currencyValueBPObjectList;
    }

    private List<CurrencyValueNBPRate> getMockCurrencyValueNBPRateList() {
        List<CurrencyValueNBPRate> currencyValueNBPRates = new ArrayList<>();

        CurrencyValueNBPRate currencyValueNBPRate1 = new CurrencyValueNBPRate("Dollar", "USD", 12.5);
        CurrencyValueNBPRate currencyValueNBPRate2 = new CurrencyValueNBPRate("Juan", "JU", 30);

        currencyValueNBPRates.add(currencyValueNBPRate1);
        currencyValueNBPRates.add(currencyValueNBPRate2);

        return currencyValueNBPRates;
    }
}
