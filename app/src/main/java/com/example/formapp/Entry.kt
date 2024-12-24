package com.example.formapp

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
    val createdAt: Long? = 0L
)