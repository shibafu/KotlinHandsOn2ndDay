package com.example

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import io.ktor.application.*
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.routing
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        anyHost()
        method(HttpMethod.Patch)
        header(HttpHeaders.ContentType)
    }
    install(ContentNegotiation){
        jackson {
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            registerModule(JavaTimeModule().apply {
                addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
                addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
            })
        }
    }
    routing {
        get("/tasks"){
            val Tasks = listOf(
                Task("今日のお仕事",
                    "今日はKotolinでAPI作るよ",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    123,
                    false),

                Task("今日のお仕事",
                    "今日はKotolinでAPI作るよ",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    124,
                    true),

                Task("今日のお仕事",
                "ウェブフレームワークを勉強するよ！",
                LocalDateTime.now(),
                LocalDateTime.now(),
                124,
                true)

            )
            call.respond(Tasks)
        }
    }
}

