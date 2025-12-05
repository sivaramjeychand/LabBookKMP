package com.labbook.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import javax.swing.JFileChooser
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LabBook KMP - Science Notebook") {
        App(
            onSave = { content ->
                val chooser = JFileChooser()
                chooser.fileFilter = FileNameExtensionFilter("LabBook Files (*.lab)", "lab")
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    val file = if (chooser.selectedFile.name.endsWith(".lab")) {
                        chooser.selectedFile
                    } else {
                        File(chooser.selectedFile.absolutePath + ".lab")
                    }
                    file.writeText(content)
                }
            },
            onLoad = {
                val chooser = JFileChooser()
                chooser.fileFilter = FileNameExtensionFilter("LabBook Files (*.lab)", "lab")
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    chooser.selectedFile.readText()
                } else {
                    null
                }
            }
        )
    }
}
