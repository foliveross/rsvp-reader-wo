package com.rsvp.wearos.repository

import com.rsvp.wearos.model.Book
import java.io.File

class BookRepository {
    
    fun loadBookFromFile(file: File): Book? {
        return try {
            val content = file.readText(Charsets.UTF_8)
            val title = file.nameWithoutExtension
            Book(
                title = title,
                content = content,
                fileName = file.name
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getWordsFromBook(book: Book): List<String> {
        return book.content
            .split(Regex("\\s+"))
            .filter { it.isNotEmpty() }
    }

    fun saveBookMetadata(book: Book, savePath: File) {
        try {
            if (!savePath.exists()) {
                savePath.mkdirs()
            }
            val file = File(savePath, "${book.id}.txt")
            file.writeText(book.content, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
