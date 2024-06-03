package com.demo_app.demo.service;

import com.demo_app.demo.entity.UserCurrencyAccess;
import com.demo_app.demo.model.UserCurrencyAccessDTO;
import com.demo_app.demo.repository.UserCurrencyAccessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class UserCurrencyService {
    UserCurrencyAccessRepository userCurrencyAccessRepository;

    public UserCurrencyService(UserCurrencyAccessRepository userCurrencyAccessRepository) {
        this.userCurrencyAccessRepository = userCurrencyAccessRepository;
    }

    public void saveUserCurrencyAccess(String name, String currency, double value) {
        var userCurrencyAccess = buildAndReturnUserCurrencyAccess(name, currency, value);
        userCurrencyAccessRepository.save(userCurrencyAccess);
    }

    public List<UserCurrencyAccessDTO> getAllUserCurrencyAccessDTO() {
        List<UserCurrencyAccess> userCurrencyAccessList = userCurrencyAccessRepository.findAll();
        return userCurrencyAccessList.stream().map(mapUserCurrencyAccessToDTO()).toList();
    }

    private UserCurrencyAccess buildAndReturnUserCurrencyAccess(String name, String currency, double value) {
        var userCurrencyAccess = new UserCurrencyAccess();

        userCurrencyAccess.setName(name);
        userCurrencyAccess.setCurrency(currency);
        userCurrencyAccess.setValue(value);

        return userCurrencyAccess;
    }

    private Function<UserCurrencyAccess, UserCurrencyAccessDTO> mapUserCurrencyAccessToDTO() {
        return (obj) -> UserCurrencyAccessDTO.builder()
                .name(obj.getName())
                .currency(obj.getCurrency())
                .value(obj.getValue())
                .date(obj.getDate())
                .build();
    }
}
