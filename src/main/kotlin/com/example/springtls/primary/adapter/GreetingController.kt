package com.example.springtls.primary.adapter

import com.example.springtls.primary.port.AppService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class GreetingController(val appService: AppService) {

    @GetMapping("/greeting")
    fun greeting(@RequestParam username: String): Mono<IncomingGreetingResponse> {
        appService.greeting()
        return Mono.just(IncomingGreetingResponse(message = "hey, there! $username"))
    }
}

data class IncomingGreetingResponse(val message: String)