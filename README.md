# LabBook KMP 🧪

**LabBook KMP** is a cross-platform (Desktop/Android) interactive notebook for science students. It simplifies the tedious process of **Error Propagation** in physics labs.

Instead of calculating uncertainties manually (e.g., $\delta R = \sqrt{(\delta A)^2 + (\delta B)^2}$), you simply type:
`mass = 12.5 +/- 0.1`

And the app handles the rest!

## Features
- **Live Markdown + Math**: Text editor that parses "Science Variable" syntax.
- **Auto-Error Propagation**: Built-in engine for standard error math.
- **Cross-Platform**: Built with Kotlin Multiplatform (Common Logic + Compose UI).
- **Dark Mode**: Focused writing environment.

## How to Run
This project is built with **Kotlin Multiplatform**.

### Desktop (Windows/Mac/Linux)
1.  Open the project in **IntelliJ IDEA**.
2.  Navigate to `composeApp/src/desktopMain/kotlin/com/labbook/kmp/Main.kt`.
3.  Click the Green Run Arrow.
4.  *Alternatively via Terminal:* `./gradlew run`

### Android
1.  Open the project in **Android Studio**.
2.  Select the `androidApp` run configuration.
3.  Run on an emulator or device.

## Technologies Used
- **Kotlin Multiplatform (KMP)**: 90% shared code efficiency.
- **Compose Multiplatform**: Shared UI for Desktop and Android.
- **Regex**: Custom parser engine.

## License
Apache 2.0 License - See [LICENSE](LICENSE) for details.
