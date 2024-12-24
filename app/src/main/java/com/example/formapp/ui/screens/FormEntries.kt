package com.example.formapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.formapp.Entry
import com.example.formapp.FormViewModel
import com.example.formapp.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormEntries(viewModel: FormViewModel, navController: NavController) {

    var _entries by remember { mutableStateOf(listOf<Entry>()) }
    var query by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }


    // Call getData to observe
    LaunchedEffect(Unit) {
        try {
            viewModel.getData { liveData ->
                _entries = liveData
            }
        } catch (e: Exception) {
            println("Error fetching data: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    // Search input
    val filteredEntries = remember(query, _entries) {
        if (query.isEmpty()) {
            _entries.sortedByDescending { it.createdAt }.take(5)
        } else {
            _entries.filter {
                it.orderNumber.contains(query, ignoreCase = true) || it.crateNumber.contains(
                    query,
                    ignoreCase = true
                )
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Search input field
                TextField(value = query, onValueChange = { query = it }, placeholder = {
                    Text(
                        text = "Search by Order or Crate number", color = Color.White
                    )
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = Color(0xFF1976D2),
                        shape = RoundedCornerShape(16.dp)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )


                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (filteredEntries.isEmpty()) {
                        item {
                            Text(
                                text = "No results found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    } else {
                        items(filteredEntries) { entry ->
                            Form(entry = entry,
                                onDeleteClick = { coroutineScope.launch { viewModel.deleteData(entry.id) } },
                                onEditClick = {
                                    navController.navigate("${Routes.FormPage.route}?entryId=${entry.id}")
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Form(entry: Entry, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Order Number: ${entry.orderNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Crate Number: ${entry.crateNumber}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Weight: ${entry.weight}", fontSize = 16.sp, color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dimensions: ${entry.length} x ${entry.width} x ${entry.height}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color(0xFFD32F2F)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Task",
                        tint = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}
