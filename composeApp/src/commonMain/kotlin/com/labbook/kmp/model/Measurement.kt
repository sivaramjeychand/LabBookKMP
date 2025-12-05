package com.labbook.kmp.model

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.abs
import com.labbook.kmp.util.formatDouble

/**
 * Represents a physical measurement with a value and an uncertainty.
 * This class implements standard error propagation logic using operator overloading.
 *
 * It resides in `commonMain` to ensure 100% code sharing between Android and Desktop.
 *
 * @property value The central value of the measurement.
 * @property uncertainty The absolute uncertainty (error) associated with the value.
 * @property manualPrecision If set, overrides scientific rounding to preserve user input format.
 */
data class Measurement(val value: Double, val uncertainty: Double = 0.0, val manualPrecision: Int? = null) {

    /**
     * Adds two measurements, propagating the uncertainty using the quadrature rule:
     * `sqrt(error1^2 + error2^2)`
     */
    operator fun plus(other: Measurement): Measurement {
        val newValue = this.value + other.value
        val newUncertainty = sqrt(this.uncertainty.pow(2) + other.uncertainty.pow(2))
        return Measurement(newValue, newUncertainty)
    }

    operator fun plus(other: Double): Measurement = this + Measurement(other, 0.0)

    operator fun minus(other: Measurement): Measurement {
        val newValue = this.value - other.value
        val newUncertainty = sqrt(this.uncertainty.pow(2) + other.uncertainty.pow(2))
        return Measurement(newValue, newUncertainty)
    }

    operator fun minus(other: Double): Measurement = this - Measurement(other, 0.0)

    operator fun times(other: Measurement): Measurement {
        val newValue = this.value * other.value
        val relativeError1 = if (this.value != 0.0) this.uncertainty / this.value else 0.0
        val relativeError2 = if (other.value != 0.0) other.uncertainty / other.value else 0.0
        val newUncertainty = abs(newValue) * sqrt(relativeError1.pow(2) + relativeError2.pow(2))
        return Measurement(newValue, newUncertainty)
    }

    operator fun times(other: Double): Measurement = this * Measurement(other, 0.0)

    operator fun div(other: Measurement): Measurement {
        val newValue = this.value / other.value
        val relativeError1 = if (this.value != 0.0) this.uncertainty / this.value else 0.0
        val relativeError2 = if (other.value != 0.0) other.uncertainty / other.value else 0.0
        val newUncertainty = abs(newValue) * sqrt(relativeError1.pow(2) + relativeError2.pow(2))
        return Measurement(newValue, newUncertainty)
    }

    operator fun div(other: Double): Measurement = this / Measurement(other, 0.0)
    
    fun pow(exponent: Double): Measurement {
        val newValue = this.value.pow(exponent)
        val newUncertainty = abs(exponent * this.value.pow(exponent - 1) * this.uncertainty)
        return Measurement(newValue, newUncertainty)
    }

    override fun toString(): String {
        if (manualPrecision != null) {
            // Case A: User explicitly typed the precision (e.g. "0.500")
            // We respect their wish exactly, just padding the value to match.
            val formattedValue = formatDouble(value, manualPrecision)
            val formattedError = formatDouble(uncertainty, manualPrecision)
            return "$formattedValue ± $formattedError"
        } else {
            // Case B: Calculated Value
            // Scientific Rule: Round Uncertainty to 1 Significant Figure, then match Value to that place.
            
            if (uncertainty == 0.0) return "$value" // Fallback

            // 1. Find magnitude of the error (e.g. 51 -> 10^1, 0.042 -> 10^-2)
            val magnitude = kotlin.math.floor(kotlin.math.log10(uncertainty))
            val scale = 10.0.pow(magnitude)

            // 2. Round error to 1 sig fig
            // e.g. 51 / 10 = 5.1 -> round(5.1) = 5 -> 5 * 10 = 50
            var roundedError = kotlin.math.round(uncertainty / scale) * scale
            
            // 3. Round value to the same magnitude
            var roundedValue = kotlin.math.round(value / scale) * scale

            // 4. Determine decimal places for string formatting
            // If magnitude is negative (decimals), we need -magnitude places.
            // If magnitude is positive (tens, hundreds), we need 0 decimal places.
            val decimalPlaces = kotlin.math.max(0, -magnitude.toInt())
            
            return "${formatDouble(roundedValue, decimalPlaces)} ± ${formatDouble(roundedError, decimalPlaces)}"
        }
    }
}
