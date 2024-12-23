package com.example.formapp

import java.util.Date

data class Entry(
    var id: String = "",
    var orderNumber: String = "",
    var crateNumber: String = "",
    val weight: String = "",
    val length: String = "",
    val width: String = "",
    val height: String = "",
    val observations: String = "",
    val user: String = "",
    val createdAt: Any? = null
) {
    fun getCreatedAtTimestamp(): Long? {
        return when (createdAt) {
            is Long -> createdAt
            is Map<*, *> -> (createdAt["time"] as? Number)?.toLong()
            else -> null
        }
    }
}