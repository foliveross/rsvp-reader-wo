package com.rsvp.wearos.model

data class Book(
    val id: String = System.currentTimeMillis().toString(),
    val title: String,
    val content: String,
    val fileName: String,
    val addedDate: Long = System.currentTimeMillis()
)

data class ReadingState(
    val currentWordIndex: Int = 0,
    val isPlaying: Boolean = false,
    val wordsPerMinute: Int = 300,
    val wordCount: Int = 0
)
