<!-- RSVP Reader for Wear OS - Development Instructions -->

# RSVP Reader Wear OS Development Guide

## Project Overview

This is an Android Wear OS application for reading TXT files using Rapid Serial Visual Presentation (RSVP) technology. The app supports gesture controls and customizable reading speeds.

## Architecture

- **Language**: Kotlin with Jetpack Compose
- **Min SDK**: 30 (Wear OS 9.0)
- **Target SDK**: 34
- **Build System**: Gradle 8.2
- **Pattern**: MVVM with Repository pattern

## Key Files

### Core Application
- `MainActivity.kt` - Entry point with file picker
- `RSVPViewModel.kt` - State management and reading logic
- `RSVPReaderScreen.kt` - UI implementation using Compose

### Services & Repository
- `RSVPService.kt` - RSVP calculation logic
- `PreferencesService.kt` - User preferences management
- `BookRepository.kt` - File operations

### Model
- `Book.kt` - Data classes for book and reading state

## Building

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Run on device
./gradlew installDebug
```

## Key Features

1. **RSVP Display**: Displays words one at a time or in chunks
2. **Gesture Control**: Swipe to navigate through words
3. **Speed Control**: WPM adjustment from 50-1000
4. **File Loading**: Direct TXT file selection
5. **Progress Tracking**: Visual progress bar

## Development Notes

- Use `DataStore` for persistent preferences, not SharedPreferences
- Compose is used for all UI - no XML layouts
- Coroutines handle async operations
- No external analytics or tracking
