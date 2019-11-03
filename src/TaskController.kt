package com.example

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import java.time.LocalDateTime

class TaskController(val taskRepository: TaskRepository) {
    suspend fun index(call: ApplicationCall){
        val tasks = this.taskRepository.findAll()
        call.respond(tasks)
    }

    suspend fun create(call : ApplicationCall) {
        //リクエストボディをタスクエンティティに変換
        val body = call.receive<TaskCreateBody>()
        val task = Task(
            id = null,
            title = body.title,
            description = body.description,
            createdDateTime = LocalDateTime.now(),
            updatedDateTime = LocalDateTime.now(),
            completed = false
        )

        // エンティティを挿入
        taskRepository.insertTask(task)
        // ステータスを設定
        call.response.status(HttpStatusCode.Created)
    }


    suspend fun show(call : ApplicationCall) {
        //リクエストボディをタスクエンティティに変換
        val id:Long? = call.parameters["id"]?.toLongOrNull()

        val task:Task? = id?.let{ taskRepository.findById(it) }
    }
}