package com.example.formapp.ui.screens


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import java.time.Instant
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormPage(modifier: Modifier = Modifier, viewModel: FormViewModel, entryId: String?) {

    val context = LocalContext.current
    val uniqueId = viewModel.database.push().key
    val users = listOf("User 1", "User 2", "User 3")

    var inputOrderNumber by remember { mutableStateOf("") }
    var inputCrateNumber by remember { mutableStateOf("") }
    var inputWeight by remember { mutableStateOf("") }
    var inputLength by remember { mutableStateOf("") }
    var inputWidth by remember { mutableStateOf("") }
    var inputHeight by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val getEntry by viewModel.entry.collectAsState()
    var checkEntryId by remember { mutableStateOf(entryId) }

    suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(getEntry) {
        if (checkEntryId != null) {
            viewModel.getDataById(entryId!!)
            getEntry?.let {
                inputOrderNumber = it.orderNumber
                inputCrateNumber = it.crateNumber
                inputWeight = it.weight
                inputLength = it.length
                inputWidth = it.width
                inputHeight = it.height
                observations = it.observations
                selectedUser = it.user
            }
        }
    }

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
                    .clickable { expanded = !expanded },
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
                            selectedUser = user
                            expanded = false
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


                        val entryIdToUse = entryId ?: uniqueId.toString()

                        val entry = Entry(
                            id = entryIdToUse,
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

                        if (checkEntryId != null) {
                            val success = viewModel.updateData(entryId!!, entry)
                            checkEntryId = null
                            showToast(
                                if (success) "Entry updated successfully!" else "Failed to update entry."
                            )
                        } else {
                            val entryExists =
                                viewModel.doesEntryExist(inputOrderNumber, inputCrateNumber)
                            if (entryExists) {
                                showToast("An entry with this Order Number and Crate Number already exists.")
                                return@launch
                            } else {
                                viewModel.addData(entry, entry.id)
                                showToast("Entry submitted successfully!")
                            }
                        }

                        withContext(Dispatchers.Main) {
                            inputOrderNumber = ""
                            inputCrateNumber = ""
                            inputWeight = ""
                            inputLength = ""
                            inputWidth = ""
                            inputHeight = ""
                            observations = ""
                            selectedUser = ""
                        }

                    } catch (e: IllegalArgumentException) {
                        showToast(e.message ?: "Validation failed.")
                    } catch (e: Exception) {
                        showToast("Error submitting form.")
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
            Text(if (checkEntryId != null) "Update" else "Submit")

        }

    }
}
