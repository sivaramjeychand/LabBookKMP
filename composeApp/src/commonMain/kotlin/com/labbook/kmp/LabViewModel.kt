package com.labbook.kmp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.labbook.kmp.model.Measurement

class LabViewModel {
    var input by mutableStateOf(
        """
        # Pendulum Experiment
        Mass:
        m = 10.0 +/- 0.5
        
        Length:
        L = 100.0 +/- 1.0
        
        # Calculate 'x' using the formula
        x = m * L
        """.trimIndent()
    )
        private set

    var variables by mutableStateOf<Map<String, Measurement>>(emptyMap())
        private set

    fun onInputChange(newInput: String) {
        input = newInput
        parseVariables()
    }

    private fun parseVariables() {
        val newVariables = mutableMapOf<String, Measurement>()
        
        // Split by lines to process sequentially (so 'x' can use 'm' defined above it)
        input.lines().forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEach

            // 1. Check for Definition: m = 10 +/- 0.5
            val definitionRegex = Regex("""^([a-zA-Z_]\w*)\s*=\s*([0-9.]+)\s*\+/-\s*([0-9.]+)$""")
            val defMatch = definitionRegex.find(trimmed)
            
            if (defMatch != null) {
                val name = defMatch.groupValues[1]
                val valueStr = defMatch.groupValues[2]
                val errorStr = defMatch.groupValues[3]
                
                val value = valueStr.toDoubleOrNull() ?: 0.0
                val error = errorStr.toDoubleOrNull() ?: 0.0
                
                // Calculate decimal places based on the uncertainty string
                val decimals = if (errorStr.contains(".")) {
                    errorStr.substringAfter(".").length
                } else {
                    0
                }
                
                newVariables[name] = Measurement(value, error, decimals)
            } else {
                // 2. Check for Expression: x = m * L
                val exprRegex = Regex("""^([a-zA-Z_]\w*)\s*=\s*(.+)$""")
                val exprMatch = exprRegex.find(trimmed)
                
                if (exprMatch != null) {
                    val name = exprMatch.groupValues[1]
                    val expression = exprMatch.groupValues[2]
                    try {
                        val result = evaluateExpression(expression, newVariables)
                        newVariables[name] = result
                    } catch (e: Exception) {
                        // Ignore invalid expressions while typing
                    }
                }
            }
        }
        variables = newVariables
    }

    // A very simple expression evaluator (Right-associative recursion or split by operators)
    // For this hackathon, we'll support simple chaining: A * B or A + B
    // NOTE: This basic version splits by space. Real parser would use Shunting Yard.
    private fun evaluateExpression(expr: String, scope: Map<String, Measurement>): Measurement {
        // Remove spaces to simplify
        // Supporting simple "A * B" or "A + B"
        // Let's try splitting by operators
        
        val token = expr.trim()
        
        // Check if it's a known variable
        if (scope.containsKey(token)) {
            return scope[token]!!
        }
        
        // Check if it's a raw number
        val rawNum = token.toDoubleOrNull()
        if (rawNum != null) {
            return Measurement(rawNum, 0.0)
        }

        // Handle Operation (Simple binary for now: A * B)
        // Order of operations is ignored in this simple hackathon version (Left to right)
        if (token.contains("*")) {
            val parts = token.split("*", limit = 2)
            return evaluateExpression(parts[0], scope) * evaluateExpression(parts[1], scope)
        }
        if (token.contains("+")) {
            val parts = token.split("+", limit = 2)
            return evaluateExpression(parts[0], scope) + evaluateExpression(parts[1], scope)
        }
        if (token.contains("/")) {
            val parts = token.split("/", limit = 2)
            return evaluateExpression(parts[0], scope) / evaluateExpression(parts[1], scope)
        }
        if (token.contains("-")) {
            val parts = token.split("-", limit = 2)
            return evaluateExpression(parts[0], scope) - evaluateExpression(parts[1], scope)
        }

        throw IllegalArgumentException("Unknown token")
    }
    
    init {
        parseVariables()
    }
}
