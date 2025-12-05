package com.labbook.kmp.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.math.abs

class MeasurementTest {
    
    private val tolerance = 0.000001

    private fun assertMeasurementEquals(expectedValue: Double, expectedUncertainty: Double, actual: Measurement) {
        if (abs(actual.value - expectedValue) > tolerance) {
            throw AssertionError("Value mismatch: Expected $expectedValue, got ${actual.value}")
        }
        if (abs(actual.uncertainty - expectedUncertainty) > tolerance) {
            throw AssertionError("Uncertainty mismatch: Expected $expectedUncertainty, got ${actual.uncertainty}")
        }
    }

    @Test
    fun testAddition() {
        val m1 = Measurement(10.0, 1.0)
        val m2 = Measurement(20.0, 2.0)
        val result = m1 + m2
        // sqrt(1^2 + 2^2) = sqrt(5) = 2.2360679
        assertMeasurementEquals(30.0, 2.2360679775, result)
    }

    @Test
    fun testMultiplication() {
        // v = 200
        // rel1 = 0.1, rel2 = 0.05
        // u = 200 * sqrt(0.01 + 0.0025) = 200 * sqrt(0.0125) = 200 * 0.111803 = 22.360679
        val m1 = Measurement(10.0, 1.0)
        val m2 = Measurement(20.0, 1.0)
        val result = m1 * m2
        assertMeasurementEquals(200.0, 22.360679775, result)
    }
    
    @Test
    fun testPower() {
        val m1 = Measurement(10.0, 0.1)
        val result = m1.pow(2.0)
        // v = 100
        // u = |2 * 10^(2-1) * 0.1| = |2 * 10 * 0.1| = 2.0
        assertMeasurementEquals(100.0, 2.0, result)
    }
}
