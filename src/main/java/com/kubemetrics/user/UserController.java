package com.kubemetrics.user;

import com.kubemetrics.user.dto.UserCreateDto;
import com.kubemetrics.user.dto.UserModelMapper;
import com.kubemetrics.user.dto.UserOutputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
class UserController {
    private final UserService userService;
    private final UserModelMapper userModelMapper;

    public UserController(UserService userService, UserModelMapper userModelMapper) {
        this.userService = userService;
        this.userModelMapper = userModelMapper;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable String id) {
        Optional<UserOutputDto> dto = userService
                .findUserById(id)
                .map(userModelMapper::toDto);
        return ResponseEntity.of(dto);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        User user = userService.createUser(userModelMapper.toModel(userCreateDto));
        return ResponseEntity
                .created(URI.create("/users/" + user.getId()))
                .build();
    }
}
