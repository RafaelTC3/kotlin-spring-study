package com.example.demo.controller

import com.example.demo.model.Message
import com.example.demo.repository.MessagesRepository
import com.example.demo.service.MessageService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class MessageControllerTest (@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper){

    @MockK
    private var repository: MessagesRepository = mockk()

    @Autowired
    private var service: MessageService = MessageService(repository)

    @Test
    fun testGet(){

        Mockito.`when`(service.findMessages()).thenReturn(listOf(
            Message("1","Hello"),
            Message("2","Ola"),
            Message("3","Hallo"),
            Message("4","Bon Jour")))

        mockMvc.get("/messages")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].text") {value("Hello!")}
            }

        verify(exactly = 1) { service.findMessages() }
    }

    @Test
    fun testPost(){
        val newMessage = Message("5", "Ohayou!")
        mockMvc.post("/messages/newMessage"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newMessage)
            accept = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isCreated()}
            }

        mockMvc.get("/messages")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].text") {value("Ohayou!")}
            }

        verify(exactly = 1) { service.post(any()) }
        verify(exactly = 1) { service.findMessages() }
    }
}
