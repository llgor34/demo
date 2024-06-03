package com.demo_app.demo.service;

import com.demo_app.demo.controller.CurrencyController;
import com.demo_app.demo.model.CurrencyValue;
import com.demo_app.demo.model.CurrencyValueRequestObj;
import com.demo_app.demo.model.UserCurrencyAccessDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CurrencyControllerTests {

    @Mock
    UserCurrencyService userCurrencyService;

    @Mock
    NBPCurrencyService nbpCurrencyService;

    @InjectMocks
    CurrencyController currencyController;

    @Test
    public void getCurrentCurrencyValueShouldReturnCurrencyValue() throws JsonProcessingException {
        CurrencyValue expectedCurrencyValue = getMockCurrencyValue();
        CurrencyValueRequestObj currencyValueRequestObj = getMockCurrencyValueRequestObj();

        Mockito.doReturn(expectedCurrencyValue).when(nbpCurrencyService).getNBPCurrencyValueByCode(Mockito.anyString());
        Mockito.doNothing().when(userCurrencyService).saveUserCurrencyAccess(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble());

        CurrencyValue actualCurrencyValue = currencyController.getCurrentCurrencyValue(currencyValueRequestObj);
        assertThat(actualCurrencyValue).isEqualTo(expectedCurrencyValue);
    }

    @Test
    public void getCurrentCurrencyValueShouldSaveUserCurrencyAccess() {
        CurrencyValue expectedCurrencyValue = getMockCurrencyValue();
        CurrencyValueRequestObj currencyValueRequestObj = getMockCurrencyValueRequestObj();

        Mockito.doReturn(expectedCurrencyValue).when(nbpCurrencyService).getNBPCurrencyValueByCode(Mockito.anyString());
        Mockito.doNothing().when(userCurrencyService).saveUserCurrencyAccess(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble());

        currencyController.getCurrentCurrencyValue(currencyValueRequestObj);
        Mockito.verify(userCurrencyService).saveUserCurrencyAccess(currencyValueRequestObj.getName(), currencyValueRequestObj.getCurrency(), expectedCurrencyValue.getValue());
    }

    @Test
    public void getCurrentCurrencyValueShouldCallGetNBPCurrencyValueByCode()  {
        CurrencyValue expectedCurrencyValue = getMockCurrencyValue();
        CurrencyValueRequestObj currencyValueRequestObj = getMockCurrencyValueRequestObj();

        Mockito.doReturn(expectedCurrencyValue).when(nbpCurrencyService).getNBPCurrencyValueByCode(currencyValueRequestObj.getCurrency());
        Mockito.doNothing().when(userCurrencyService).saveUserCurrencyAccess(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble());

        currencyController.getCurrentCurrencyValue(currencyValueRequestObj);
        Mockito.verify(nbpCurrencyService).getNBPCurrencyValueByCode(currencyValueRequestObj.getCurrency());
    }

    @Test
    public void getUserCurrencyAccessShouldReturnUserCurrencyAccessDTOList() {
        List<UserCurrencyAccessDTO> expectedUserCurrencyAccessDTOList = getMockUserCurrencyAccessDTOList();

        Mockito.doReturn(expectedUserCurrencyAccessDTOList).when(userCurrencyService).getAllUserCurrencyAccessDTO();

        List<UserCurrencyAccessDTO> actualUserCurrencyAccessDTOList = currencyController.getUserCurrencyAccessDTO();
        assertThat(actualUserCurrencyAccessDTOList).isEqualTo(expectedUserCurrencyAccessDTOList);
    }

    @Test
    public void getUserCurrencyAccessShouldGetAllUserCurrencyAccessDTO() {
        Mockito.doReturn(null).when(userCurrencyService).getAllUserCurrencyAccessDTO();
        currencyController.getUserCurrencyAccessDTO();
        Mockito.verify(userCurrencyService).getAllUserCurrencyAccessDTO();
    }

    private CurrencyValue getMockCurrencyValue() {
        return new CurrencyValue(3.5);
    }

    private CurrencyValueRequestObj getMockCurrencyValueRequestObj() {
        return new CurrencyValueRequestObj("USD", "John Doe");
    }

    private List<UserCurrencyAccessDTO> getMockUserCurrencyAccessDTOList() {
        List<UserCurrencyAccessDTO> userCurrencyAccessDTOList = new ArrayList<>();
        UserCurrencyAccessDTO userCurrencyAccessDTO1 = new UserCurrencyAccessDTO("USD", "John Doe", new Timestamp(System.currentTimeMillis()), 3.5);
        UserCurrencyAccessDTO userCurrencyAccessDTO2 = new UserCurrencyAccessDTO("EUR", "John Smith", new Timestamp(System.currentTimeMillis()), 4.5);

        userCurrencyAccessDTOList.add(userCurrencyAccessDTO1);
        userCurrencyAccessDTOList.add(userCurrencyAccessDTO2);

        return userCurrencyAccessDTOList;
    }
}
