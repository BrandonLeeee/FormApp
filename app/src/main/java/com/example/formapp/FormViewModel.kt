package com.example.formapp

import android.annotation.SuppressLint
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class FormViewModel {
    private val urlDb = "https://rosalapp-f86e9-default-rtdb.asia-southeast1.firebasedatabase.app"
    val database = FirebaseDatabase.getInstance(urlDb).reference.child("orders")
    private val _entry = MutableStateFlow<Entry?>(null)
    val entry: StateFlow<Entry?> get() = _entry

    fun getData(onDataChanged: (List<Entry>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rawData = snapshot.children.mapNotNull { it.getValue(Entry::class.java) }
                onDataChanged(rawData)
            }

            override fun onCancelled(e: DatabaseError) {
                println("Error fetching data: ${e.message}")
            }
        })
    }


    suspend fun getDataById(entryId: String) {
        try {
            val snapshot = database.child(entryId).get().await()
            if (snapshot.exists()) {
                val fetchedEntry = snapshot.getValue(Entry::class.java)
                _entry.value = fetchedEntry
            } else {
                println("No data exists for entryId: $entryId")
            }
        } catch (e: Exception) {
            println("Error fetching data: ${e.message}")
        }
    }

    // Check the database for an existing entry with the same orderNumber and crateNumber
    suspend fun doesEntryExist(orderNumber: String, crateNumber: String): Boolean {
        return try {
            val query = database.orderByChild("orderNumber").equalTo(orderNumber)
            val snapshot = query.get().await()
            snapshot.children.any { it.child("crateNumber").value == crateNumber }
        } catch (e: Exception) {
            println("Error fetching data: ${e.message}")
            false
        }
    }


    suspend fun addData(entry: Entry, id: String) {
        database.child(id).setValue(entry).await()
    }


    suspend fun updateData(id: String, updatedEntry: Entry): Boolean {
        return try {
            database.child(id).setValue(updatedEntry).await()
            true
        } catch (e: Exception) {
            println("Error fetching entry: ${e.message}")
            false
        }
    }


    suspend fun deleteData(entryId: String) {
        database.child(entryId).removeValue().await()
    }


}