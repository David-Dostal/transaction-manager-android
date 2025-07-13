# Transaction Manager

A localized Kotlin Android app using Jetpack Compose to manage personal finances, featuring a stateful UI, modular architecture, and data visualization.

## 📱 Features

- Track and categorize income and expenses
- View weekly, monthly, and yearly financial statistics
- Add, edit, and delete transactions
- Export financial graphs
- Localized in two languages
- Designed with Figma

## 🧰 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM
- **Database**: Room
- **Storage**: DataStore
- **Build System**: Gradle
- **Testing**: Android Instrumentation Testing
- **Others**: ProGuard, Git

## 📂 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cz/mendelu/pef/xdostal8/transactionmanager/
│   │   │       ├── architecture/         # BaseViewModel & core MVVM logic
│   │   │       ├── database/             # Room DAO, DB, Repository
│   │   │       ├── datastore/            # Preferences with DataStore
│   │   │       └── ui/                   # Jetpack Compose screens
```

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/yourusername/transaction-manager.git
   ```
2. Open in Android Studio
3. Run on emulator or physical device