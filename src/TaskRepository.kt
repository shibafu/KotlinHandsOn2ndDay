package com.example

interface TaskRepository {
    fun findAll(): List<Task>
}