package com.kubemetrics.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class UserCreateDto(@get:NotBlank val firstName: String,
                         @get:NotBlank val lastName: String,
                         @get:Email val email: String)

data class UserOutputDto(val id: String,
                         val firstName: String,
                         val lastName: String,
                         val email: String)

object UserModelMapper {
    fun toModel(userCreateDto: UserCreateDto): User {
        return User(firstName = userCreateDto.firstName,
                    lastName = userCreateDto.lastName,
                    email = userCreateDto.email)
    }

    fun toDto(user: User): UserOutputDto {
        return UserOutputDto(id = user.id!!,
                             firstName = user.firstName,
                             lastName = user.lastName,
                             email = user.email)
    }
}

