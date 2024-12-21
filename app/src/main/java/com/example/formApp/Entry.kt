package com.example.formApp

data class Entry(
    var id: String? = null,
    var orderNumber: Int = 0,
    var crateNumber: Int = 0,
    val weight: Int = 0,
    val length: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val observations: String = "",
    val user: String = ""
)