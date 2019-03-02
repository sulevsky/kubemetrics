package com.kubemetrics.user

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@Validated
@RestController
class UserController(val userService: UserService) {
    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<UserOutputDto> {
        return userService
                .findUserById(id)
                .map { UserModelMapper.toDto(it) }
                .let { ResponseEntity.of(it) }

    }

    @PostMapping("/users")
    fun createUser(@RequestBody @Valid userCreateDto: UserCreateDto): ResponseEntity<Void> {
        val user = userService.createUser(UserModelMapper.toModel(userCreateDto))
        return ResponseEntity
                .created(URI.create("/users/${user.id}"))
                .build()
    }
}
