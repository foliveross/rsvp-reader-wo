package com.rsvp.wearos.service

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "reading_preferences")

class PreferencesService(private val context: Context) {
    
    companion object {
        private val WORDS_PER_MINUTE = intPreferencesKey("words_per_minute")
        private val WORDS_AT_ONCE = intPreferencesKey("words_at_once")
        private val AUTO_PLAY = booleanPreferencesKey("auto_play")
        private val CURRENT_BOOK_ID = stringPreferencesKey("current_book_id")
    }

    val wordsPerMinute: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[WORDS_PER_MINUTE] ?: 300
    }

    val wordsAtOnce: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[WORDS_AT_ONCE] ?: 1
    }

    val autoPlay: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[AUTO_PLAY] ?: false
    }

    val currentBookId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_BOOK_ID]
    }

    suspend fun setWordsPerMinute(wpm: Int) {
        context.dataStore.edit { preferences ->
            preferences[WORDS_PER_MINUTE] = wpm
        }
    }

    suspend fun setWordsAtOnce(count: Int) {
        context.dataStore.edit { preferences ->
            preferences[WORDS_AT_ONCE] = count
        }
    }

    suspend fun setAutoPlay(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_PLAY] = enabled
        }
    }

    suspend fun setCurrentBookId(bookId: String?) {
        context.dataStore.edit { preferences ->
            if (bookId != null) {
                preferences[CURRENT_BOOK_ID] = bookId
            } else {
                preferences.remove(CURRENT_BOOK_ID)
            }
        }
    }
}
