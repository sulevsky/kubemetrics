package com.kubemetrics.user.dto;

import com.kubemetrics.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    public User toModel(UserCreateDto userCreateDto) {
        return new User(null,
                        userCreateDto.getFirstName(),
                        userCreateDto.getLastName(),
                        userCreateDto.getEmail());
    }

    public UserOutputDto toDto(User user) {
        return new UserOutputDto(user.getId(),
                                 user.getFirstName(),
                                 user.getLastName(),
                                 user.getEmail());
    }
}
