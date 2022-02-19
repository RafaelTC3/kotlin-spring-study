package com.example.demo.service

import com.example.demo.model.Message
import com.example.demo.repository.MessagesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageService (@Autowired val db: MessagesRepository) {

    fun findMessages(): List<Message>{
        return db.findMessages()
    }

    fun post(message: Message){
        db.save(message)
    }
}