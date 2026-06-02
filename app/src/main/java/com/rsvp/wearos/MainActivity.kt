package com.rsvp.wearos

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsvp.wearos.ui.RSVPReaderScreen
import com.rsvp.wearos.viewmodel.RSVPViewModel

class MainActivity : ComponentActivity() {
    
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            rsvpViewModel?.loadBookFromUri(it)
        }
    }
    
    private var rsvpViewModel: RSVPViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val viewModel: RSVPViewModel = viewModel()
            rsvpViewModel = viewModel
            
            MaterialTheme {
                RSVPReaderScreen(
                    viewModel = viewModel,
                    onLoadBook = {
                        filePickerLauncher.launch("text/plain")
                    }
                )
            }
        }
    }
}
