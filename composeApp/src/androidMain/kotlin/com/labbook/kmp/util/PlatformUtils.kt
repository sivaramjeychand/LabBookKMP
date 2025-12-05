package com.labbook.kmp.util

actual fun formatDouble(value: Double, decimalPlaces: Int): String {
    return String.format("%.${decimalPlaces}f", value)
}
