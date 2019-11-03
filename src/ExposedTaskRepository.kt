package com.example

import com.example.TaskTable.createdDateTime
import com.example.TaskTable.description
import com.example.TaskTable.title
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.time.LocalDateTime
import java.time.ZoneId

class ExposedTaskRepository:TaskRepository {
    override fun findAll(): List<Task> {
        return transaction{
            TaskTable.selectAll()
                .orderBy(TaskTable.createdDateTime, SortOrder.DESC)
                .map {
                    Task(
                        id = it[TaskTable.id].value,
                        title = it[TaskTable.title],
                        description = it[TaskTable.description],
                        createdDateTime = it[TaskTable.createdDateTime].toStandardLocalDateTime(),
                        updatedDateTime = it[TaskTable.createdDateTime].toStandardLocalDateTime(),
                        completed = it[TaskTable.isCompleted]
                    )
                }
        }
    }

    override fun insertTask(task: Task){
        return transaction{
            TaskTable.insert {
                it[title] = task.title
                it[description] = task.description
                it[createdDateTime] = task.createdDateTime.toJodaDateTime()
                it[updatedDateTime] = task.updatedDateTime.toJodaDateTime()
                it[isCompleted] = task.completed
            }
        }
    }

    override fun findById(id:Long): Task? {
        return transaction {
            TaskTable.select { TaskTable.id eq id }
                .map {
                    Task(
                        id = it[TaskTable.id].value,
                        title = it[TaskTable.title],
                        description = it[TaskTable.description],
                        createdDateTime = it[TaskTable.createdDateTime].toStandardLocalDateTime(),
                        updatedDateTime = it[TaskTable.updatedDateTime].toStandardLocalDateTime(),
                        completed = it[TaskTable.isCompleted]
                    )
                }
                .firstOrNull()
        }
    }
    private fun DateTime.toStandardLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(toDate().toInstant(), ZoneId.systemDefault())
    }

    private fun LocalDateTime.toJodaDateTime(): DateTime {
        return DateTime(atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }
}