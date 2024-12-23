package com.example.formapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FormViewModel {
    private val urlDb = "https://rosalapp-f86e9-default-rtdb.asia-southeast1.firebasedatabase.app"

    val database = FirebaseDatabase.getInstance(urlDb).reference.child("orders")


    suspend fun getData(): List<Entry> {
        val snapshot: DataSnapshot = database.get().await()

        val entries = mutableListOf<Entry>()

        snapshot.children.forEach { orderSnapshot ->
            if (orderSnapshot.exists()) {
                orderSnapshot.children.forEach { entrySnapshot ->
                    val entry = entrySnapshot.getValue(Entry::class.java)
                    if (entry != null) {
                        entries.add(entry)
                    }
                }
            }
        }

        return entries
    }

    suspend fun addData(entry: Entry, orderNumber: String, crateNumber: String) {
        val updatedEntry = entry.copy(orderNumber = orderNumber, crateNumber = crateNumber)
        database.child(orderNumber).child(crateNumber).setValue(updatedEntry).await() }

    suspend fun UpdateData(entry: Entry) {
        database.child(entry.id).updateChildren(entry.toMap()).await()
    }

    suspend fun deleteData(entryId: String) {
        database.child(entryId).removeValue().await()
    }


    private fun Entry.toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "orderNumber" to orderNumber,
            "crateNumber" to crateNumber,
            "weight" to weight,
            "length" to length,
            "width" to width,
            "height" to height,
            "observations" to observations,
            "user" to user
        )
    }

}