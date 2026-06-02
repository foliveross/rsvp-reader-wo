package com.rsvp.wearos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rsvp.wearos.viewmodel.RSVPViewModel

@Composable
fun RSVPReaderScreen(
    viewModel: RSVPViewModel,
    onLoadBook: () -> Unit
) {
    val currentBook by viewModel.currentBook.collectAsStateWithLifecycle()
    val currentWord by viewModel.currentWord.collectAsStateWithLifecycle()
    val readingState by viewModel.readingState.collectAsStateWithLifecycle()
    val wpm by viewModel.wpm.collectAsStateWithLifecycle()
    val progress by viewModel.progressPercentage.collectAsStateWithLifecycle()
    val wordsAtOnce by viewModel.wordsAtOnce.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        if (currentBook != null) {
            Text(
                text = currentBook!!.title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }

        // Main RSVP Display
        if (currentBook != null) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .clickable {
                        if (readingState.isPlaying) {
                            viewModel.pauseReading()
                        } else if (readingState.currentWordIndex > 0) {
                            viewModel.resumeReading()
                        }
                    }
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            when {
                                dragAmount > 50 -> viewModel.previousWord()
                                dragAmount < -50 -> viewModel.nextWord()
                            }
                            change.consume()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentWord,
                        color = Color.Red,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "$wpm WPM | Words: $wordsAtOnce",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .clickable(onClick = onLoadBook),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Book Loaded",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap to load a book",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        }

        // Progress Bar
        if (currentBook != null) {
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color.Red,
                trackColor = Color.DarkGray
            )
        }

        // Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentBook != null) {
                IconButton(
                    onClick = { viewModel.decreaseWPM() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FastRewind,
                        contentDescription = "Decrease Speed",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = { viewModel.previousWord() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = {
                        if (readingState.isPlaying) {
                            viewModel.pauseReading()
                        } else {
                            if (readingState.currentWordIndex == 0) {
                                viewModel.startReading()
                            } else {
                                viewModel.resumeReading()
                            }
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (readingState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (readingState.isPlaying) "Pause" else "Play",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = { viewModel.nextWord() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = { viewModel.increaseWPM() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = "Increase Speed",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Button(
                    onClick = onLoadBook,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Load Book")
                }
            }
        }
    }
}
