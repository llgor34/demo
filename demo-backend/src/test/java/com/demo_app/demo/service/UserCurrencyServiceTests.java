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
        userCurrencyService.saveUserCurrencyAccess("Bob bob", "USD", 3.50);
        Mockito.verify(userCurrencyAccessRepository).save(Mockito.any(UserCurrencyAccess.class));
    }

    @Test
    public void getAllUserCurrencyAccessShouldCallRepositoryFindAll() {
        userCurrencyService.getAllUserCurrencyAccess();
        Mockito.verify(userCurrencyAccessRepository).findAll();
    }

    @Test
    public void getAllUserCurrencyAccessShouldReturnList() {
        List<UserCurrencyAccess> userCurrencyAccessList = new ArrayList<>();
        UserCurrencyAccess userCurrencyAccess = new UserCurrencyAccess();

        userCurrencyAccess.setCurrency("USD");
        userCurrencyAccess.setId(1l);
        userCurrencyAccessList.add(userCurrencyAccess);

        List<UserCurrencyAccessDTO> userCurrencyAccessDTOList = new ArrayList<>();
        UserCurrencyAccessDTO userCurrencyAccessDTO = new UserCurrencyAccessDTO();
        userCurrencyAccessDTO.setCurrency("USD");
        userCurrencyAccessDTOList.add(userCurrencyAccessDTO);

        Mockito.when(userCurrencyAccessRepository.findAll()).thenReturn(userCurrencyAccessList);
        var userCurrencyAccessDTOListReturned = userCurrencyService.getAllUserCurrencyAccess();

        assertEquals(userCurrencyAccessDTOListReturned, userCurrencyAccessDTOList);
    }
}
