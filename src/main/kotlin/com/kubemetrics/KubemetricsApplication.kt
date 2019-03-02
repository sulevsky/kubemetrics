package com.kubemetrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KubemetricsApplication

fun main(args: Array<String>) {
    runApplication<KubemetricsApplication>(*args)
}
