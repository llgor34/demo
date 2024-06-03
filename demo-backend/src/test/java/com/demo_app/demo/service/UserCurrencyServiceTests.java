package com.demo_app.demo.service;

import com.demo_app.demo.entity.UserCurrencyAccess;
import com.demo_app.demo.model.UserCurrencyAccessDTO;
import com.demo_app.demo.repository.UserCurrencyAccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserCurrencyServiceTests {

    @Mock
    private UserCurrencyAccessRepository userCurrencyAccessRepository;

    @InjectMocks
    UserCurrencyService userCurrencyService;

    @Test
    public void saveUserCurrencyAccessShouldCallRepositorySave() {
        UserCurrencyAccess userCurrencyAccess = getMockUserCurrencyAccess();

        userCurrencyService.saveUserCurrencyAccess(userCurrencyAccess.getName(), userCurrencyAccess.getCurrency(), userCurrencyAccess.getValue());
        Mockito.verify(userCurrencyAccessRepository).save(userCurrencyAccess);
    }

    @Test
    public void getAllUserCurrencyAccessShouldCallRepositoryFindAllDTO() {
        userCurrencyService.getAllUserCurrencyAccessDTO();
        Mockito.verify(userCurrencyAccessRepository).findAll();
    }

    @Test
    public void getAllUserCurrencyAccessDTOShouldReturnList() {
        List<UserCurrencyAccess> userCurrencyAccessList = getMockUserCurrencyAccessList();
        List<UserCurrencyAccessDTO> userCurrencyAccessDTOList = getMockUserCurrencyAccessDTOList();


        Mockito.when(userCurrencyAccessRepository.findAll()).thenReturn(userCurrencyAccessList);
        var userCurrencyAccessDTOListReturned = userCurrencyService.getAllUserCurrencyAccessDTO();

        assertEquals(userCurrencyAccessDTOListReturned, userCurrencyAccessDTOList);
    }

    private List<UserCurrencyAccess> getMockUserCurrencyAccessList() {
        List<UserCurrencyAccess> userCurrencyAccessList = new ArrayList<>();
        UserCurrencyAccess userCurrencyAccess = getMockUserCurrencyAccess();
        userCurrencyAccessList.add(userCurrencyAccess);

        return userCurrencyAccessList;
    }

    private List<UserCurrencyAccessDTO> getMockUserCurrencyAccessDTOList() {
        List<UserCurrencyAccessDTO> userCurrencyAccessDTOList = new ArrayList<>();
        UserCurrencyAccessDTO userCurrencyAccessDTO = getMockUserCurrencyAccessDTO();
        userCurrencyAccessDTOList.add(userCurrencyAccessDTO);

        return userCurrencyAccessDTOList;
    }

    private UserCurrencyAccess getMockUserCurrencyAccess() {
        UserCurrencyAccess userCurrencyAccess = new UserCurrencyAccess();
        userCurrencyAccess.setName("John Doe");
        userCurrencyAccess.setCurrency("USD");
        userCurrencyAccess.setValue(3.5);

        return userCurrencyAccess;
    }

    private UserCurrencyAccessDTO getMockUserCurrencyAccessDTO() {
        UserCurrencyAccessDTO userCurrencyAccessDTO = new UserCurrencyAccessDTO();
        userCurrencyAccessDTO.setName("John Doe");
        userCurrencyAccessDTO.setCurrency("USD");
        userCurrencyAccessDTO.setValue(3.5);

        return userCurrencyAccessDTO;
    }
}
