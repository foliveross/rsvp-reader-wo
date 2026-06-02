# Contributing to RSVP Reader

We love your input! We want to make contributing to RSVP Reader as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## Development Process

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

### Pull Requests

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Use Kotlin idioms
- Follow Android development best practices
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions small and focused

### Testing

Before submitting:
1. Build the project: `gradlew build`
2. Run tests: `gradlew test`
3. Test on device: `adb install app/build/outputs/apk/debug/app-debug.apk`

## Reporting Bugs

When reporting a bug, include:
- Your Wear OS device model and version
- Kotlin/Android Studio version
- Detailed steps to reproduce
- Expected behavior
- Actual behavior
- Screenshots or logs if applicable

## Proposing Features

When proposing features:
- Explain use case
- Describe expected behavior
- Provide mockups or examples if applicable
- Consider backwards compatibility

## License

By contributing, you agree that your contributions will be licensed under its MIT License.

## Community

- Follow the [Code of Conduct](CODE_OF_CONDUCT.md)
- Be respectful and inclusive
- Help others in the community

Thank you for contributing to RSVP Reader! 🎉
