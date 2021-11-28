package com.technical.challenge.operator.controller

import com.technical.challenge.APIException
import com.technical.challenge.operator.request.BillRequest
import com.technical.challenge.operator.request.RepresentativeRequest
import com.technical.challenge.operator.request.TransferRequest
import com.technical.challenge.operator.service.UserAccountOperationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class UserAccountOperationController(@Autowired val service : UserAccountOperationService) {

    @PutMapping("/account/{userId}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@PathVariable("userId") userId: Long, @RequestBody request: RepresentativeRequest) {
        try {
            service.withdraw(userId, request.value)
        } catch(e : Exception) {
            throw APIException(e.message!!)
        }
    }

    @PutMapping("/account/{userId}/deposit")
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@PathVariable("userId") userId: Long, @RequestBody request: RepresentativeRequest) {
        service.deposit(userId, request.value)
    }

    @PutMapping("/account/{fromUserId}/transfer/{toUserId}")
    @ResponseStatus(HttpStatus.OK)
    fun transfer(
        @PathVariable("fromUserId") fromUserId: Long,
        @PathVariable("toUserId") toUserId: Long,
        @RequestBody transferRequest: TransferRequest
    ) {
        service.transfer(fromUserId, toUserId, transferRequest)
    }

    @PutMapping("/account/{userId}/bill")
    @ResponseStatus(HttpStatus.OK)
    fun payBill(
        @PathVariable("userId") userId: Long, @RequestBody billRequest: BillRequest) {
        service.payBill(userId, billRequest)
    }
}