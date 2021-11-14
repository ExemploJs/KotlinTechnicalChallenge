package com.technical.challenge.history.consumer

import com.technical.challenge.history.service.HistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HistoryController(@Autowired val service : HistoryService) {

    @GetMapping("/account/{accountId}/history")
    fun findHistoriesByAccountId(@PathVariable("accountId") accountId : Long) = service.findHistoriesByAccountId(accountId)
}