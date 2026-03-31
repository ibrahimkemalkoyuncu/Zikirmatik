# Zikirmatik - Dhikr Counter App

A modern, habit-forming mobile application for counting Islamic dhikr (remembrance of Allah) with clean architecture and smooth user experience.

## Features

### 🧠 Smart Counter
- **Tap and Hold**: Increment counter with intuitive touch interaction
- **Haptic Feedback**: Vibrates on every tap for tactile confirmation
- **Smooth Animations**: Scale and ripple effects for visual feedback
- **Milestone Feedback**: Special notification every 33 counts (traditional Islamic milestone)

### 📿 Zikr Selection
- **Predefined Zikrs**: Subhanallah (سُبْحَانَ اللَّهِ), Alhamdulillah (الْحَمْدُ لِلَّهِ), Allahu Akbar (اللَّهُ أَكْبَرُ)
- **Safe Switching**: Counter resets when changing zikr to avoid confusion
- **Arabic Display**: Beautiful Arabic text with English translation

### 💾 Persistence
- **Instant Restore**: App remembers your current count and selected zikr
- **DataStore**: Uses modern Android DataStore for reliable local storage
- **State Preservation**: Handles app kills, orientation changes, and device restarts

### 🎯 Daily Goal
- **Customizable Target**: Set your daily dhikr goal (default: 100)
- **Real-time Progress**: Visual progress bar updates as you count
- **Motivation**: Clear indication of your daily achievement

### 🔥 Streak System
- **Consecutive Days**: Track how many days you've maintained your dhikr practice
- **Automatic Reset**: Resets if you miss a day, encouraging consistency
- **Last Active Date**: Smart calculation of streaks based on actual usage

## Screenshots

*(Add screenshots here when available)*

## Architecture

Built with Clean Architecture principles:

```
presentation/
├── ui/          # Compose screens and components
├── viewmodel/   # MVVM ViewModels with StateFlow
└── components/  # Reusable UI components

domain/
├── model/       # Data models (Zikr, CounterState, Goal, Streak)
└── usecase/     # Business logic use cases

data/
├── local/       # DataStore, HapticManager
└── repository/  # Data access layer
```

### Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM + Clean Architecture
- **State Management**: StateFlow (no LiveData)
- **Persistence**: DataStore (for preferences)
- **Future**: Room (for history/stats)
- **Dependency Injection**: Manual DI (Hilt-compatible structure)

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ibrahimkemalkoyuncu/Zikirmatik.git
   cd Zikirmatik
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Run the app**:
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon) in Android Studio
   - Or use terminal: `./gradlew installDebug`

## Usage

### Getting Started
1. **Launch the App**: No login required - starts instantly
2. **Select Zikr**: Choose from Subhanallah, Alhamdulillah, or Allahu Akbar
3. **Start Counting**: Tap the large central button to increment
4. **View Progress**: Monitor your daily goal and streak

### Daily Practice
- **Morning/Evening**: Set aside time for dhikr
- **Consistency**: Aim for daily practice to build streaks
- **Milestones**: Celebrate every 33 counts as a spiritual milestone

### Customization
- **Goal Setting**: Adjust daily target in settings (future feature)
- **Themes**: Light/Dark mode support (Material 3)

## Requirements

- **Android**: API 36+ (Android 16)
- **Kotlin**: 2.2.10+
- **Android Studio**: Latest stable version

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -am 'Add feature'`
4. Push to branch: `git push origin feature-name`
5. Submit a pull request

## Future Enhancements

### Phase 2 (Retention Engine)
- **Daily Missions**: Generate simple daily tasks
- **Smart Notifications**: Reminders when inactive (WorkManager)
- **Stats Dashboard**: Daily/weekly totals with charts (Room DB)
- **Social Features**: Share streaks with friends

### Technical Improvements
- **Offline Support**: Cache zikr data
- **Multi-language**: Support for different Islamic traditions
- **Accessibility**: Screen reader support
- **Performance**: Optimize for low-end devices

## License

This project is open source. Feel free to use, modify, and distribute.

## Acknowledgments

- Built with ❤️ for the Muslim community
- Inspired by traditional dhikr beads and modern habit-tracking apps
- Uses Islamic texts from reliable sources

---

**Note**: This app is designed to help with spiritual practice. For accurate Islamic guidance, consult qualified scholars.</content>
<parameter name="filePath">C:\Users\afney\AndroidStudioProjects\AfneyZikirmatik\README.md
