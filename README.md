# HTML শিখি — Android App

বাংলায় HTML শেখার সম্পূর্ণ Android App। Beginner থেকে Advanced পর্যন্ত ১৫টি Chapter, ৩০+ Lesson, Live Code Editor, Tag Reference, Projects ও Quiz।

---

## Features

- ১৫টি Chapter, ৩০+ Lesson — সব বাংলায়
- Live Code Editor — WebView output সহ
- HTML Tag Reference — সার্চযোগ্য
- ১০টি Project — সহজ থেকে কঠিন
- প্রতিটি Lesson-এ Quiz
- XP, Level, Streak, Badges
- Activity Calendar
- Android Native Notification
- প্রতিদিনের পড়ার লক্ষ্য ও রিমাইন্ডার
- Room Database — সব data সংরক্ষণ

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Database:** Room
- **Storage:** DataStore Preferences
- **Background:** WorkManager
- **Notification:** Android NotificationManager
- **Build:** GitHub Actions

---

## Termux দিয়ে GitHub-এ Push করার নিয়ম

### ১. প্রথমবার Setup

```bash
# Termux-এ Git install করো
pkg update && pkg upgrade -y
pkg install git -y

# GitHub credentials set করো
git config --global user.name "তোমার নাম"
git config --global user.email "তোমার_email@gmail.com"

# SSH key তৈরি করো (একবারই করতে হবে)
ssh-keygen -t ed25519 -C "তোমার_email@gmail.com"
cat ~/.ssh/id_ed25519.pub
# এই key টি GitHub Settings > SSH Keys-এ add করো
```

### ২. Repository তৈরি ও Push

```bash
# GitHub-এ নতুন repo তৈরি করো: HTMLLearnApp

# Phone-এ project folder-এ যাও
cd /sdcard/HTMLLearnApp
# অথবা যেখানে extract করেছো

# Git init
git init
git remote add origin git@github.com:তোমার_username/HTMLLearnApp.git

# সব file add করো
git add .
git commit -m "Initial commit: HTML Learn App"

# Push করো
git push -u origin main
```

### ৩. APK Download

GitHub Actions auto build শুরু হবে।

```
GitHub → তোমার repo → Actions → Build APK → Artifacts → APK download করো
```

### ৪. পরবর্তী update push করতে

```bash
cd /sdcard/HTMLLearnApp
git add .
git commit -m "Update: বিবরণ"
git push
```

---

## Project Structure

```
HTMLLearnApp/
├── .github/workflows/build.yml    ← Auto APK builder
├── app/src/main/
│   ├── kotlin/com/htmllearn/app/
│   │   ├── MainActivity.kt
│   │   ├── HtmlLearnApp.kt
│   │   ├── content/               ← সব lesson, quiz, project data
│   │   ├── data/                  ← Room DB, DataStore
│   │   ├── notification/          ← Workers, NotificationHelper
│   │   ├── ui/                    ← Compose screens, components
│   │   └── viewmodel/
│   └── res/
├── app/build.gradle.kts
└── settings.gradle.kts
```

---

## License

MIT License — শিক্ষামূলক ব্যবহারের জন্য।
