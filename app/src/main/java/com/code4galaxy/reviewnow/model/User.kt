package com.code4galaxy.reviewnow.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val isSuspended: Boolean = false,
    val profileImageUrl: String? = null,
    val userType: String = "user"
)
