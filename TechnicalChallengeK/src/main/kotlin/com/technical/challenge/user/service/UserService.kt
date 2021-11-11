package com.technical.challenge.user.service

import com.technical.challenge.UserNotFoundException
import com.technical.challenge.user.entity.User
import com.technical.challenge.user.repository.UserRepository
import com.technical.challenge.user.request.UserRequest
import com.technical.challenge.user.response.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException
import java.util.*

@Service
class UserService(@Autowired val repository : UserRepository) {

    @Transactional
    fun create(req: UserRequest) {
        repository.save(User(null, req.name, Date()))
    }

    fun get(id : Long) : UserResponse = repository
            .findById(id)
            .map { user -> UserResponse(user.name, user.creationDate) }
            .orElseThrow { UserNotFoundException() }
}
