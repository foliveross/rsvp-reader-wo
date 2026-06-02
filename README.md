# RSVP Reader for Wear OS

A sophisticated Rapid Serial Visual Presentation (RSVP) reader application designed for Wear OS smartwatches. Load TXT files and read books at customizable speeds with intuitive gesture controls.

## Features

- **📖 TXT File Support**: Load any TXT file directly from your device
- **⚡ RSVP Reading**: Rapid Serial Visual Presentation with configurable words per minute (50-1000 WPM)
- **👆 Gesture Controls**: Swipe left/right to navigate through words
- **⏯️ Auto-play**: Automatic word progression with customizable speed
- **🎨 Optimized UI**: Clean, minimalist design optimized for small Wear OS screens
- **⚙️ Customizable**: Adjust reading speed, words displayed at once, and more
- **💾 Progress Tracking**: Visual progress bar showing reading progress

## Requirements

- Android Studio Giraffe or later
- Wear OS 9.0 (API level 30) or higher
- Gradle 8.0 or higher
- Java 11+

## Building

### Prerequisites
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/rsvp_reader_ws.git
   cd rsvp_reader_ws
   ```

2. Open in Android Studio or build via command line

### Build from Command Line

```bash
# For debug build
./gradlew build

# For release build
./gradlew assembleRelease
```

### Build with Android Studio

1. Open Android Studio
2. File → Open → Select the project root
3. Build → Make Project (Ctrl+F9)

## Installation

1. Connect your Wear OS device
2. Install the APK:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

Or use Android Studio's Run button.

## Usage

1. **Launch the App**: Open RSVP Reader on your Wear OS device
2. **Load a Book**: Tap "Load Book" or click in the center area
3. **Select a TXT File**: Choose any TXT file from your device
4. **Start Reading**:
   - Tap the **Play** button (red play icon) to start
   - Tap the **Pause** button to pause
5. **Control Reading**:
   - **Swipe Left**: Next word(s)
   - **Swipe Right**: Previous word(s)
   - **Tap Center**: Pause/Resume reading
   - **Speed Down** (⏪): Decrease WPM
   - **Speed Up** (⏩): Increase WPM
   - **Skip** (⏭️/⏮️): Jump forward/backward

## Configuration

### Reading Speed

Adjust Words Per Minute (WPM) from 50 to 1000:
- Use the Speed buttons on the control panel
- Each tap changes speed by 50 WPM

### Words at Once

Configure how many words display simultaneously (1-5):
- Can be modified in settings (future enhancement)

## Project Structure

```
rsvp_reader_ws/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/rsvp/wearos/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   ├── ui/
│   │   │   │   └── viewmodel/
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       └── layout/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Architecture

The app follows **MVVM** (Model-View-ViewModel) architecture:

- **Model**: `Book`, `ReadingState` - Data classes
- **Repository**: `BookRepository` - File and data management
- **Service**: `RSVPService`, `PreferencesService` - Business logic
- **ViewModel**: `RSVPViewModel` - UI state management
- **UI**: `RSVPReaderScreen` - Compose-based UI

## Key Components

### RSVPViewModel
Manages reading state, word progression, and user interactions

### RSVPService
Handles RSVP logic:
- WPM calculation
- Word chunking
- Progress tracking

### PreferencesService
Manages user preferences using DataStore:
- Reading speed
- Words at once
- Current book ID

### BookRepository
Handles file operations:
- Loading TXT files
- Parsing content
- Saving metadata

## Permissions

The app requires:
- `READ_EXTERNAL_STORAGE` - To load TXT files

## Future Enhancements

- [ ] Support for EPUB format
- [ ] Multiple reading modes (RSVP, Page, Line)
- [ ] Bookmarks and notes
- [ ] Audio cues for word transitions
- [ ] Advanced statistics (WPM, total read time)
- [ ] Dark/Light theme support
- [ ] Cloud backup of reading progress
- [ ] TTS fallback for accessibility

## Troubleshooting

### App won't open files
- Ensure you have storage permissions
- Try moving TXT file to a different location
- Check file encoding (UTF-8 recommended)

### Reading speed too fast/slow
- Adjust WPM using control buttons
- WPM affects delay between words (60000ms / WPM)

### Crashes on startup
- Clear app cache: Settings → Apps → RSVP Reader → Storage → Clear Cache
- Reinstall the app

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

MIT License - See LICENSE file for details

## Support

For issues, feature requests, or questions:
- Open an issue on GitHub
- Check existing issues for solutions

## Authors

- RSVP Reader Development Team

## Acknowledgments

- Wear OS team for excellent documentation
- Jetpack Compose for modern UI toolkit
- Android community for support and inspiration

---

**Made with ❤️ for Wear OS**
