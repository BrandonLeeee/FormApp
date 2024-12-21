package com.example.formApp


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun FormPage(modifier: Modifier = Modifier) {


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
    val entries = remember { mutableStateListOf<Entry>() }
    val context = LocalContext.current
    val urlDb =
        "https://rosalapp-f86e9-default-rtdb.asia-southeast1.firebasedatabase.app"
    val databaseReference =
        FirebaseDatabase.getInstance(urlDb)
            .reference.child(
                "encomendas"
            )
    val users = listOf("User 1", "User 2", "User 3") // Sample list of users


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Controlo de Peso",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Order Number Field
        OutlinedTextField(
            value = inputOrderNumber,
            onValueChange = { inputOrderNumber = it },
            label = { Text(text = "Nº Enc:") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Crate Number Field
        OutlinedTextField(
            value = inputCrateNumber,
            onValueChange = { inputCrateNumber = it },
            label = { Text(text = "Nº Cx:") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Weight Field
        OutlinedTextField(
            value = inputWeight,
            onValueChange = { inputWeight = it },
            label = { Text(text = "Peso:") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Dimensions Fields
        OutlinedTextField(
            value = inputLength,
            onValueChange = { inputLength = it },
            label = { Text(text = "Comprimento:") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        OutlinedTextField(
            value = inputWidth,
            onValueChange = { inputWidth = it },
            label = { Text(text = "Largura:") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        OutlinedTextField(
            value = inputHeight,
            onValueChange = { inputHeight = it },
            label = { Text(text = "Altura:") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Observations Field
        OutlinedTextField(
            value = observations,
            onValueChange = { observations = it },
            label = { Text(text = "Observações:") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            maxLines = 3
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

        // Dropdown for Responsible User
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedUser,
                onValueChange = {},
                label = { Text(text = "Responsável:") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
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

        fun addOrUpdateEntry(orderNumber: Int, entry: Entry) {
            val crateKey = entry.crateNumber.toString() // Use crateNumber as the key

            databaseReference.child(orderNumber.toString()).child(crateKey).setValue(entry)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        inputOrderNumber = ""
                        inputCrateNumber = ""
                        inputWeight = ""
                        inputLength = ""
                        inputWidth = ""
                        inputHeight = ""
                        observations = ""
                        selectedUser = ""
                        Toast.makeText(context, "Submetido com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Erro ao submeter o formulário.",
                            Toast.LENGTH_SHORT
                        ).show()
                        println("Error: ${task.exception?.message}")
                    }
                }
        }

        Button(
            onClick = {
                val entry = Entry(
                    id = if (entries.isEmpty()) "1" else (entries.size + 1).toString(),
                    orderNumber = inputOrderNumber.toIntOrNull() ?: 0,
                    crateNumber = inputCrateNumber.toIntOrNull() ?: 0,
                    weight = inputWeight.toIntOrNull() ?: 0,
                    length = inputLength.toIntOrNull() ?: 0,
                    width = inputWidth.toIntOrNull() ?: 0,
                    height = inputHeight.toIntOrNull() ?: 0,
                    observations = observations,
                    user = selectedUser
                )
                addOrUpdateEntry(entry.orderNumber, entry)
            }
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        DisposableEffect(Unit) {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val newEntries = mutableListOf<Entry>()
                        snapshot.children.forEach { orderSnapshot ->
                            val orderNumber = orderSnapshot.key?.toIntOrNull()
                            if (orderNumber != null) {
                                orderSnapshot.children.forEach { crateSnapshot ->
                                    crateSnapshot.getValue(Entry::class.java)?.apply {
                                        this.orderNumber = orderNumber
                                        this.crateNumber = crateSnapshot.key?.toIntOrNull() ?: 0
                                        newEntries.add(this)
                                    }
                                }
                            }
                        }
                        entries.clear()
                        entries.addAll(newEntries)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Firebase Error: ${error.message}")
                }
            }

            databaseReference.addValueEventListener(listener)

            onDispose {
                databaseReference.removeEventListener(listener)
            }
        }

        // Display the data in a LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(entries) { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Order Number: ${entry.orderNumber}")
                        Text(text = "Crate Number: ${entry.crateNumber}")
                        Text(text = "Weight: ${entry.weight}")
                        Text(text = "Dimensions: ${entry.length} x ${entry.width} x ${entry.height}")
                        Text(text = "Observations: ${entry.observations}")
                        Text(text = "Responsible User: ${entry.user}")
                    }
                }
            }
        }
    }
}

