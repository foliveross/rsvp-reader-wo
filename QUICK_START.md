# RSVP Reader - Quick Start Guide

## 🚀 Deployment to GitHub

Your project is ready to be deployed to GitHub! Follow these simple steps:

### Option 1: Automatic Deployment (Recommended)

#### Using GitHub CLI (Fastest)
```powershell
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"
.\deploy.ps1 -UseGHCLI
```

This will:
1. Authenticate with GitHub (if needed)
2. Create the repository `rsvp_reader_ws`
3. Push all files to GitHub
4. Display your repository URL

#### Using Manual Method
```powershell
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"
.\deploy.ps1
```

Then follow the interactive prompts.

### Option 2: Manual Setup

1. Create repository on GitHub:
   - Go to https://github.com/new
   - Repository name: `rsvp_reader_ws`
   - Visibility: Public
   - Click "Create repository"

2. Push from command line:
```bash
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"
git remote add origin https://github.com/YOUR_USERNAME/rsvp_reader_ws.git
git branch -M main
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

---

## 🏗️ Build Instructions

### Prerequisites
- JDK 11 or higher
- Android SDK (API level 34)
- (Optional) Android Studio

### Build from Command Line

```bash
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"

# Debug build
gradlew assembleDebug

# Release build (requires signing)
gradlew assembleRelease
```

### Build with Android Studio
1. Open Android Studio
2. File → Open → Select project root
3. Build → Make Project (Ctrl+F9)

---

## 📦 Project Structure

```
rsvp_reader_ws/
├── app/                          # Main application module
│   ├── src/main/
│   │   ├── java/com/rsvp/wearos/
│   │   │   ├── MainActivity.kt      # Entry point
│   │   │   ├── model/               # Data models
│   │   │   ├── repository/          # Data access
│   │   │   ├── service/             # Business logic
│   │   │   ├── ui/                  # UI components
│   │   │   └── viewmodel/           # UI state
│   │   └── res/                     # Resources
│   └── build.gradle.kts
├── .github/
│   └── workflows/build.yml          # CI/CD configuration
├── gradle/wrapper/                  # Gradle wrapper
├── build.gradle.kts                 # Root build config
├── settings.gradle.kts              # Project settings
├── README.md                        # Full documentation
├── DEPLOYMENT.md                    # Deployment guide
└── LICENSE                          # MIT License
```

---

## 🎯 Features

### Core Features
- ✅ **RSVP Reading** - Display words at configurable speeds (50-1000 WPM)
- ✅ **TXT File Support** - Load any text file
- ✅ **Gesture Controls** - Swipe left/right to navigate
- ✅ **Auto-play** - Automatic word progression
- ✅ **Progress Tracking** - Visual progress bar

### UI Controls
- **Play/Pause** - Red button toggles reading
- **Skip Forward/Back** - Navigate through words
- **Speed Up/Down** - Adjust WPM (±50 per click)
- **Tap to Load** - Load TXT files

### Settings
- Words Per Minute: 50-1000
- Words at Once: 1-5 (configurable)
- Auto-save preferences

---

## 🔧 Configuration

### Customizable Parameters

Edit in [RSVPViewModel.kt](app/src/main/java/com/rsvp/wearos/viewmodel/RSVPViewModel.kt):

```kotlin
// Default WPM (words per minute)
_wpm.value = 300

// Default words displayed at once
_wordsAtOnce.value = 1

// Minimum WPM
val newWPM = maxOf(_wpm.value - 50, 50)

// Maximum WPM
val newWPM = minOf(_wpm.value + 50, 1000)
```

---

## 📱 Testing on Device

### Prerequisites
- Wear OS 9.0+ smartwatch
- USB cable
- ADB (Android Debug Bridge)

### Installation
```bash
# Enable USB debugging on your Wear OS device
# Then run:
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Debug
```bash
# View logs from your device
adb logcat
```

---

## 🐛 Troubleshooting

### Build Errors

**Java version mismatch:**
```bash
java -version  # Should be 11+
```

**Gradle cache issues:**
```bash
gradlew clean
gradlew build
```

### Git/GitHub Issues

**Authentication fails:**
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

**Repository already exists:**
```bash
# If you get "remote origin already exists"
git remote remove origin
# Then run deploy.ps1 again
```

---

## 🔄 GitHub Actions CI/CD

The project includes automatic build configuration:

**Triggered on:**
- Push to `main` branch
- Push to `develop` branch
- Pull requests to `main`

**Generates:**
- Debug APK
- Release APK (if signing configured)
- Build artifacts

**Location:**
- `.github/workflows/build.yml`

---

## 📚 Additional Resources

- [Wear OS Documentation](https://developer.android.com/wear)
- [Jetpack Compose Guide](https://developer.android.com/jetpack/compose)
- [Android Development](https://developer.android.com)
- [Git Documentation](https://git-scm.com/doc)
- [GitHub Help](https://docs.github.com)

---

## ✅ Next Steps

1. **Deploy to GitHub** (see section above)
2. **Configure GitHub Actions** in repository settings
3. **Share Repository** with collaborators
4. **Create Releases** with APK files
5. **Distribute to Users** via GitHub Releases

---

## 📄 License

This project is released under the **MIT License** - see [LICENSE](LICENSE) file for details.

---

Made with ❤️ for Wear OS
