package com.kubemetrics.user

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class UserService(val userRepository: UserRepository) {
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun findUserById(id: String): Optional<User> {
        return userRepository.findById(id)
    }
}

interface UserRepository : CrudRepository<User, String>

data class User(val id: String? = null,
                val firstName: String,
                val lastName: String,
                val email: String)

