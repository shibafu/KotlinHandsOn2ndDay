package com.example

import io.ktor.application.ApplicationCall
import io.ktor.response.respond

class TaskController(val TaskRepository: TaskRepository) {
    suspend fun index(call: ApplicationCall){
        val tasks = this.TaskRepository.findAll()
        call.respond(tasks)
    }
}