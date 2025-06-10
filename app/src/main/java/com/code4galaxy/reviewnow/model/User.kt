package com.code4galaxy.reviewnow.model

/*userId (document)
├── name
├── email
├── profilePicUrl
├── isSuspended: Boolean
└── userType: "admin" or "user"*/
data class User(
    val userId: Long,
    val name: String,
    val email: String,
    val profilePicUrl: String,
    val isSuspended: Boolean,
    val userType: String
)
