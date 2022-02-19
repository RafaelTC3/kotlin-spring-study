package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class FirstKotlinProjectApplication

fun main(args: Array<String>) {
	runApplication<FirstKotlinProjectApplication>(*args)
}