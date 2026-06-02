# Deployment Guide - RSVP Reader

This guide will help you deploy the RSVP Reader Wear OS app to GitHub.

## Prerequisites

- Git installed on your machine
- GitHub account
- GitHub CLI (optional but recommended)

## Step 1: Create Repository on GitHub

### Option A: Using GitHub CLI (Recommended)

```bash
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"
gh repo create rsvp_reader_ws --public --source=. --remote=origin --push
```

### Option B: Using Web Interface

1. Go to [GitHub.com](https://github.com)
2. Click "+" in top right corner → "New repository"
3. Repository name: `rsvp_reader_ws`
4. Description: "RSVP Reader for Wear OS - Reading TXT files with Rapid Serial Visual Presentation"
5. Select "Public"
6. DO NOT initialize with README, .gitignore, or license (we already have them)
7. Click "Create repository"

## Step 2: Configure Remote (If using Web Interface)

After creating the repository on GitHub, run:

```bash
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"
git remote add origin https://github.com/YOUR_USERNAME/rsvp_reader_ws.git
git branch -M main
git push -u origin main
```

Replace `YOUR_USERNAME` with your actual GitHub username.

## Step 3: Enable GitHub Actions (Optional but Recommended)

The repository includes CI/CD workflows that will:
- Build the app automatically on push
- Generate APK artifacts
- Run tests

These are already configured in `.github/workflows/build.yml`

1. Go to Settings → Actions → General
2. Ensure Actions are enabled
3. Under "Workflow permissions", select "Read and write permissions"

## Step 4: Verify Deployment

1. Visit `https://github.com/YOUR_USERNAME/rsvp_reader_ws`
2. You should see:
   - All source files committed
   - README.md with project documentation
   - Actions tab showing build workflows

## Step 5: Create Release (Optional)

To create a release with compiled APKs:

```bash
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"
git tag -a v1.0.0 -m "Initial release: RSVP Reader v1.0.0"
git push origin v1.0.0
```

This will trigger the build workflow and create a release with APK downloads.

## Build Commands for Local Development

### Build APK Locally

```bash
cd "c:\Users\theda\OneDrive\Documents\proyectos\lector rsvp"

# Debug build
gradlew assembleDebug

# Release build (requires signing key)
gradlew assembleRelease

# Run tests
gradlew test
```

### Install on Device

```bash
# Requires Android device connected via USB with ADB enabled
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Troubleshooting

### Git Push Fails with Authentication Error

Update your credentials:
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

Or use GitHub CLI:
```bash
gh auth login
```

### Build Fails Locally

1. Ensure Java 11+ is installed: `java -version`
2. Clear cache: `gradlew clean`
3. Rebuild: `gradlew build`

### Actions Don't Run

1. Check Actions permissions in Repository Settings
2. Ensure workflow file exists at `.github/workflows/build.yml`
3. Check workflow status in Actions tab

## Next Steps

1. Push to GitHub
2. Create releases for distribution
3. (Optional) Set up branch protection rules
4. (Optional) Configure code scanning and security features

## Support

For issues with deployment:
1. Check GitHub's documentation: https://docs.github.com
2. Review Git documentation: https://git-scm.com/doc
3. Check Android Studio build errors locally first
