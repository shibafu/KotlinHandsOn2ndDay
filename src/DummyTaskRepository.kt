package com.example

import java.time.LocalDateTime

class DummyTaskRepository: TaskRepository {
    override fun findAll(): List<Task> {
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
        return Tasks
    }
}