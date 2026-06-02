package com.rsvp.wearos.service

import kotlinx.coroutines.delay

class RSVPService {
    
    /**
     * Calculate delay between words based on WPM
     * @param wpm Words per minute
     * @return Delay in milliseconds
     */
    fun getDelayForWPM(wpm: Int): Long {
        return (60000 / wpm).toLong()
    }

    /**
     * Get a chunk of words to display at once
     * @param words List of words
     * @param startIndex Starting index
     * @param wordsCount Number of words to display
     * @return Chunk of words to display
     */
    fun getWordChunk(words: List<String>, startIndex: Int, wordsCount: Int): String {
        if (words.isEmpty() || startIndex >= words.size) {
            return ""
        }
        
        val endIndex = minOf(startIndex + wordsCount, words.size)
        return words.subList(startIndex, endIndex).joinToString(" ")
    }

    /**
     * Check if we've reached the end of the book
     */
    fun isEndOfBook(currentIndex: Int, totalWords: Int): Boolean {
        return currentIndex >= totalWords
    }

    /**
     * Get progress percentage
     */
    fun getProgressPercentage(currentIndex: Int, totalWords: Int): Float {
        return if (totalWords == 0) 0f else (currentIndex.toFloat() / totalWords) * 100f
    }
}
