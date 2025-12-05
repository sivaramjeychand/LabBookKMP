# LabBook KMP ⚛️

**A Cross-Platform Scientific Notebook for Physicists.**
*Built with Kotlin Multiplatform, Compose, and a custom Error Propagation Engine.*

## 💡 The Problem

Physics students currently have a disconnected workflow: write notes in a physical book, switch to Excel for error propagation (`=SQRT((A2/B2)^2 + ...)`), and then manually copy results back. This is slow and prone to transcription errors.

## 🚀 The Solution

**LabBook KMP** treats mathematical uncertainty as a first-class citizen.

* **Live Parsing:** Type `m = 10.0 ± 0.5` and the app understands it as a `Measurement` object.
* **Auto-Propagation:** Formulas like `F = m * a` automatically calculate the resulting uncertainty ($\delta F$) using standard quadrature rules.
* **SigFig Formatting:** Results are automatically rounded to the correct significant figures for scientific reporting.

## 🛠 Tech Stack

* **Language:** Kotlin (100%)
* **UI:** Compose Multiplatform (Desktop + Android)
* **Architecture:**
    * `commonMain`: Contains the **Parser**, **Math Engine**, and **State Management**.
    * `desktopApp` / `androidApp`: Platform-specific entry points and File I/O.
* **State:** Custom ViewModels with reactive UI state.

## 📦 Installation & Setup

### Prerequisites

* JDK 17 or higher
* Android Studio (Ladybug or newer recommended) / IntelliJ IDEA

### How to Run (Desktop)

1.  Clone the repository:
    ```bash
    git clone https://github.com/YOUR_USERNAME/LabBookKMP.git
    cd LabBookKMP
    ```
2.  Run the desktop distribution task:
    ```bash
    ./gradlew :desktopApp:run
    ```

### How to Run (Android)

1.  Open the project in Android Studio.
2.  Select the `androidApp` configuration.
3.  Connect a device or start an emulator.
4.  Click **Run**.

## 🧪 Features

* [x] **Markdown-style Editor:** Write text and math naturally.
* [x] **Real-time Math Engine:** Supports `+`, `-`, `*`, `/` with error propagation.
* [x] **Cross-Platform Storage:** Save `.lab` files on Desktop and Android.
* [x] **Significant Figures:** Automatic formatting for scientific precision.

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](https://www.google.com/search?q=LICENSE) file for details.

-----