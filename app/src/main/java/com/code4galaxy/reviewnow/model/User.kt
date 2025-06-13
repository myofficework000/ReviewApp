package com.code4galaxy.reviewnow.model



data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val isSuspended: Boolean,
    val profileImageUrl: String? = null,
    // this can be "admin" as well
    val userType: String = "user"
)
