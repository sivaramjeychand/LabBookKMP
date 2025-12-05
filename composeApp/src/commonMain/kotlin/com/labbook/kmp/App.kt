package com.labbook.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App(
    onSave: (String) -> Unit = {},
    onLoad: () -> String? = { null }
) {
    val viewModel = remember { LabViewModel() }

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color(0xFFBB86FC),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopAppBar(
                title = { Text("LabBook KMP") },
                backgroundColor = MaterialTheme.colors.surface,
                actions = {
                    Button(onClick = { 
                        val loaded = onLoad()
                        if (loaded != null) viewModel.onInputChange(loaded)
                    }) {
                        Text("Open")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onSave(viewModel.input) }) {
                        Text("Save")
                    }
                }
            )

            Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
                // Left Pane: Editor
                OutlinedTextField(
                    value = viewModel.input,
                    onValueChange = { viewModel.onInputChange(it) },
                    modifier = Modifier.weight(1f).fillMaxSize().padding(8.dp),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 14.sp, color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedBorderColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.White
                    )
                )

                // Right Pane: Live Results
                Column(modifier = Modifier.weight(1f).fillMaxSize().padding(8.dp).background(MaterialTheme.colors.surface)) {
                    Text(
                        "Live Variables",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                    
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                        items(viewModel.variables.toList()) { (name, measurement) ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "$name = ",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colors.secondary)
                                )
                                Text(
                                    text = measurement.toString(),
                                    style = TextStyle(fontFamily = FontFamily.Monospace, color = Color.White)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
