package com.example.formapp.ui.screens


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formapp.Entry
import com.example.formapp.FormViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormPage(modifier: Modifier = Modifier, repository: FormViewModel) {

    var inputOrderNumber by remember { mutableStateOf("") }
    var inputCrateNumber by remember { mutableStateOf("") }
    var inputWeight by remember { mutableStateOf("") }
    var inputLength by remember { mutableStateOf("") }
    var inputWidth by remember { mutableStateOf("") }
    var inputHeight by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var sendFileWithoutPhoto by remember { mutableStateOf(false) }
    var selectedFile by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf("") }
    val context = LocalContext.current
    val uniqueId = repository.database.push().key
    val users = listOf("User 1", "User 2", "User 3") // Sample list of users

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Controlo de Peso",
            fontSize = 32.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = inputOrderNumber,
            onValueChange = { inputOrderNumber = it },
            label = { Text(text = "Nº Enc:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        OutlinedTextField(
            value = inputCrateNumber,
            onValueChange = { inputCrateNumber = it },
            label = { Text(text = "Nº Cx:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        OutlinedTextField(
            value = inputWeight,
            onValueChange = { inputWeight = it },
            label = { Text(text = "Peso:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        OutlinedTextField(
            value = inputLength,
            onValueChange = { inputLength = it },
            label = { Text(text = "Comprimento:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        OutlinedTextField(
            value = inputWidth,
            onValueChange = { inputWidth = it },
            label = { Text(text = "Largura:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        OutlinedTextField(
            value = inputHeight,
            onValueChange = { inputHeight = it },
            label = { Text(text = "Altura:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        OutlinedTextField(
            value = observations,
            onValueChange = { observations = it },
            label = { Text(text = "Observações:", color = Color(0xFF1976D2)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            maxLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1976D2),
                unfocusedBorderColor = Color.LightGray
            )
        )

        // Checkbox
        /*Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = sendFileWithoutPhoto,
                onCheckedChange = { sendFileWithoutPhoto = it }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Enviar ficheiro sem foto?")
        }

        // File Picker Placeholder
        OutlinedTextField(
            value = selectedFile,
            onValueChange = { selectedFile = it },
            label = { Text(text = "Escolher ficheiro") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )*/

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedUser,
                onValueChange = {},
                label = { Text(text = "Responsável:", color = Color(0xFF1976D2)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }, // Toggle the dropdown on click
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color.LightGray
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { Text(user) },
                        onClick = {
                            selectedUser = user // Set selected user
                            expanded = false    // Close dropdown
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        require(inputOrderNumber.isNotEmpty()) { "Order Number cannot be empty" }
                        require(inputCrateNumber.isNotEmpty()) { "Crate Number cannot be empty" }
                        require(inputWeight.isNotEmpty()) { "Weight cannot be empty" }
                        require(inputLength.isNotEmpty()) { "Length cannot be empty" }
                        require(inputWidth.isNotEmpty()) { "Width cannot be empty" }
                        require(inputHeight.isNotEmpty()) { "Height cannot be empty" }
                        require(selectedUser.isNotEmpty()) { "User cannot be empty" }

                        val entry = Entry(
                            id = uniqueId.toString(),
                            orderNumber = inputOrderNumber,
                            crateNumber = inputCrateNumber,
                            weight = inputWeight,
                            length = inputLength,
                            width = inputWidth,
                            height = inputHeight,
                            observations = observations,
                            user = selectedUser,
                            createdAt = Date.from(Instant.now()).time
                        )

                        // Add the entry to Firebase, where a unique ID will be assigned
                        repository.addData(entry, entry.orderNumber, entry.crateNumber)

                        withContext(Dispatchers.Main) {
                            // Clear input fields and show success message
                            inputOrderNumber = ""
                            inputCrateNumber = ""
                            inputWeight = ""
                            inputLength = ""
                            inputWidth = ""
                            inputHeight = ""
                            observations = ""
                            selectedUser = ""
                            Toast.makeText(context, "Submetido com sucesso!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } catch (e: IllegalArgumentException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                e.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Erro ao submeter formulário.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        println("Error: ${e.message}")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        ) {
            Text("Submeter")
        }
    }
}
