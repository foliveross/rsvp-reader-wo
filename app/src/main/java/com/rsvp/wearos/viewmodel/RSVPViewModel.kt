package com.rsvp.wearos.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rsvp.wearos.model.Book
import com.rsvp.wearos.model.ReadingState
import com.rsvp.wearos.repository.BookRepository
import com.rsvp.wearos.service.PreferencesService
import com.rsvp.wearos.service.RSVPService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class RSVPViewModel(application: Application) : AndroidViewModel(application) {
    
    private val bookRepository = BookRepository()
    private val rsvpService = RSVPService()
    private val preferencesService = PreferencesService(application)
    private val contentResolver: ContentResolver = application.contentResolver
    
    // UI State
    private val _readingState = MutableStateFlow(ReadingState())
    val readingState: StateFlow<ReadingState> = _readingState.asStateFlow()
    
    private val _currentBook = MutableStateFlow<Book?>(null)
    val currentBook: StateFlow<Book?> = _currentBook.asStateFlow()
    
    private val _currentWord = MutableStateFlow("")
    val currentWord: StateFlow<String> = _currentWord.asStateFlow()
    
    private val _allWords = MutableStateFlow<List<String>>(emptyList())
    val allWords: StateFlow<List<String>> = _allWords.asStateFlow()
    
    private val _progressPercentage = MutableStateFlow(0f)
    val progressPercentage: StateFlow<Float> = _progressPercentage.asStateFlow()
    
    private val _wpm = MutableStateFlow(300)
    val wpm: StateFlow<Int> = _wpm.asStateFlow()
    
    private val _wordsAtOnce = MutableStateFlow(1)
    val wordsAtOnce: StateFlow<Int> = _wordsAtOnce.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        viewModelScope.launch {
            preferencesService.wordsPerMinute.collect { wpm ->
                _wpm.value = wpm
            }
        }
        
        viewModelScope.launch {
            preferencesService.wordsAtOnce.collect { count ->
                _wordsAtOnce.value = count
            }
        }
    }
    
    fun loadBookFromUri(uri: Uri) {
        viewModelScope.launch {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    val content = stream.bufferedReader().use { it.readText() }
                    val fileName = getFileNameFromUri(uri)
                    val title = fileName.substringBeforeLast(".")
                    
                    val book = Book(
                        title = title,
                        content = content,
                        fileName = fileName
                    )
                    
                    _currentBook.value = book
                    _allWords.value = bookRepository.getWordsFromBook(book)
                    _readingState.value = ReadingState(wordCount = _allWords.value.size)
                    _errorMessage.value = null
                    
                    preferencesService.setCurrentBookId(book.id)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading file: ${e.message}"
                e.printStackTrace()
            }
        }
    }
    
    private fun getFileNameFromUri(uri: Uri): String {
        return try {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndex("_display_name")
                    if (columnIndex >= 0) {
                        it.getString(columnIndex)
                    } else {
                        uri.lastPathSegment ?: "book"
                    }
                } else {
                    "book"
                }
            } ?: "book"
        } catch (e: Exception) {
            uri.lastPathSegment ?: "book"
        }
    }
    
    fun startReading() {
        _readingState.value = _readingState.value.copy(isPlaying = true)
        viewModelScope.launch {
            playRSVP()
        }
    }
    
    fun pauseReading() {
        _readingState.value = _readingState.value.copy(isPlaying = false)
    }
    
    fun resumeReading() {
        _readingState.value = _readingState.value.copy(isPlaying = true)
        viewModelScope.launch {
            playRSVP()
        }
    }
    
    fun nextWord() {
        val currentIndex = _readingState.value.currentWordIndex
        val newIndex = minOf(currentIndex + _wordsAtOnce.value, _allWords.value.size)
        updateWordIndex(newIndex)
    }
    
    fun previousWord() {
        val currentIndex = _readingState.value.currentWordIndex
        val newIndex = maxOf(currentIndex - _wordsAtOnce.value, 0)
        updateWordIndex(newIndex)
    }
    
    private fun updateWordIndex(newIndex: Int) {
        _readingState.value = _readingState.value.copy(currentWordIndex = newIndex)
        updateCurrentWord()
    }
    
    private fun updateCurrentWord() {
        val index = _readingState.value.currentWordIndex
        val words = _allWords.value
        val count = _wordsAtOnce.value
        _currentWord.value = rsvpService.getWordChunk(words, index, count)
        _progressPercentage.value = rsvpService.getProgressPercentage(index, words.size)
    }
    
    private suspend fun playRSVP() {
        val words = _allWords.value
        var currentIndex = _readingState.value.currentWordIndex
        val delay = rsvpService.getDelayForWPM(_wpm.value)
        val wordsToShow = _wordsAtOnce.value
        
        while (_readingState.value.isPlaying && currentIndex < words.size) {
            updateWordIndex(currentIndex)
            delay(delay)
            currentIndex += wordsToShow
        }
        
        if (currentIndex >= words.size) {
            _readingState.value = _readingState.value.copy(isPlaying = false)
            _currentWord.value = "Finished!"
        }
    }
    
    fun setWordsPerMinute(wpm: Int) {
        viewModelScope.launch {
            preferencesService.setWordsPerMinute(wpm)
            _wpm.value = wpm
        }
    }
    
    fun setWordsAtOnce(count: Int) {
        viewModelScope.launch {
            preferencesService.setWordsAtOnce(count)
            _wordsAtOnce.value = count
        }
    }
    
    fun increaseWPM() {
        val newWPM = minOf(_wpm.value + 50, 1000)
        setWordsPerMinute(newWPM)
    }
    
    fun decreaseWPM() {
        val newWPM = maxOf(_wpm.value - 50, 50)
        setWordsPerMinute(newWPM)
    }
    
    fun resetReading() {
        _readingState.value = ReadingState(wordCount = _allWords.value.size)
        _currentWord.value = ""
        _progressPercentage.value = 0f
    }
}
