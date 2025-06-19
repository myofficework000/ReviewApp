package com.code4galaxy.reviewnow.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.User
import com.code4galaxy.reviewnow.model.data.local.preferences.UserPreferenceManager
import com.code4galaxy.reviewnow.view.RegisterState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState



    private val _adminName = mutableStateOf("Admin")
    val adminName: State<String> = _adminName

    private val _adminEmail = mutableStateOf("admin@example.com")
    val adminEmail: State<String> = _adminEmail



    fun registerUser(
        email: String,
        password: String,
        confirmPassword: String,
        selectedUserType: String
    ) {


        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _registerState.value = RegisterState.Error("Please fill all fields")
            return
        }

        if (password != confirmPassword) {
            _registerState.value = RegisterState.Error("Passwords do not match")
            return
        }

        _registerState.value = RegisterState.Loading

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener
                    val user = User(id = uid, name = "", isSuspended = false, email = email)

                    if (selectedUserType == "admin") {
                        firestore.collection("admins")
                            .document(uid)
                            .set(mapOf("name" to email.substringBefore('@')))
                            .addOnSuccessListener {


                                _registerState.value = RegisterState.Success
                            }
                            .addOnFailureListener {
                                _registerState.value = RegisterState.Error("Failed to save admin: ${it.message}")
                            }
                    } else {
                        firestore.collection("users")
                            .document(uid)
                            .set(user)
                            .addOnSuccessListener {


                                _registerState.value = RegisterState.Success
                            }
                            .addOnFailureListener {
                                _registerState.value = RegisterState.Error("Failed to save user: ${it.message}")
                            }
                    }
                    userPreferenceManager.saveUserType(selectedUserType)
                    userPreferenceManager.saveId(uid)
                    println("id:${userPreferenceManager.getId()}")

                } else {
                    _registerState.value = RegisterState.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }

    fun resetRegisterState() {
        _registerState.value = RegisterState.Idle
    }

    fun logout(context: Context, onLoggedOut: () -> Unit) {
        firebaseAuth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(context, gso)
        googleClient.signOut()
            .addOnCompleteListener {
                onLoggedOut()
            }
            .addOnFailureListener {
                onLoggedOut()
            }
    }

    fun loadAdminDetails() {
        val user = firebaseAuth.currentUser
        if (user != null) {
            _adminEmail.value = user.email ?: "admin@example.com"

            firestore.collection("admins").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _adminName.value = document.getString("name") ?: "Admin"
                    }
                }
                .addOnFailureListener {
                    _adminName.value = "Admin"
                }
        }
    }
}
