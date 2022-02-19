package com.example.demo.controller

import com.example.demo.model.Message
import com.example.demo.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController (@Autowired private val service: MessageService){

    @GetMapping
    fun getMessages(): List<Message>{
        return service.findMessages()
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/newMessage")
    fun post(@RequestBody message: Message) {
        service.post(message)
    }
}