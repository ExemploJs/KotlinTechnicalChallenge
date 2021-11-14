package com.technical.challenge.account.controller

import com.technical.challenge.account.request.AccountCreationRequest
import com.technical.challenge.account.response.AccountResponse
import com.technical.challenge.account.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class AccountController(val service: AccountService) {

    @PostMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable("id") id: Long, @RequestBody request: AccountCreationRequest) {
        this.service.create(id, request)
    }

    @PutMapping("/user/{id}/account/inactivate")
    @ResponseStatus(HttpStatus.OK)
    fun inactivate(@PathVariable("id") id: Long) {
        this.service.inactivate(id)
    }

    @GetMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getAccount(@PathVariable("id") id: Long) = this.service.getAccount(id)
}