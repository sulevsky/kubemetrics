package com.kubemetrics.kubemetrics

import io.micrometer.core.annotation.Timed
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KubemetricsApplication

@RestController
class HelloController(@Value("\${message}") val message: String) {
    @Timed
    @GetMapping("/greeting")
    fun getGreeting(): String {
        return message
    }
}

fun main(args: Array<String>) {
    runApplication<KubemetricsApplication>(*args)
}

