package com.technical.challenge.user.controller

import com.technical.challenge.user.request.UserRequest
import com.technical.challenge.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController(@Autowired val service : UserService) {

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req : UserRequest) {
        service.create(req)
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable("id") id : Long) = service.get(id)
}