package com.code4galaxy.reviewnow.model

data class Review(
    val id: String = "",
    val userId: String = "",
    val brandId: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
