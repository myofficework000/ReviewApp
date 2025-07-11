package com.code4galaxy.reviewnow.viewmodel

import android.content.Context
import android.util.Log
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
    private var googleSignOutHandled = false

    private val _adminName = mutableStateOf("Admin")
    val adminName: State<String> = _adminName

    private val _adminEmail = mutableStateOf("admin@example.com")
    val adminEmail: State<String> = _adminEmail

    fun loadAdminDetails() {
        val user = firebaseAuth.currentUser
        if (user != null) {
            firestore.collection("admins").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _adminName.value = document.getString("name") ?: "Admin"
                        _adminEmail.value = document.getString("email") ?: user.email ?: "admin@example.com"
                    }
                }
                .addOnFailureListener {
                    _adminName.value = "Admin"
                    _adminEmail.value = user.email ?: "admin@example.com"
                }
        }
    }


    fun registerUser(email: String, password: String, confirmPassword: String, selectedUserType: String) {
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
                    val user = User(
                        id = uid,
                        name = email.substringBefore('@'),
                        email = email,
                        isSuspended = false,
                        userType = selectedUserType
                    )

                    val collection = if (selectedUserType == "admin") "admins" else "users"

                    firestore.collection(collection)
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            _registerState.value = RegisterState.Success
                        }
                        .addOnFailureListener {
                            _registerState.value = RegisterState.Error("Failed to save user: ${it.message}")
                        }

                    userPreferenceManager.saveUserType(selectedUserType)
                    userPreferenceManager.saveId(uid)
                } else {
                    _registerState.value = RegisterState.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }

    fun resetRegisterState() {
        _registerState.value = RegisterState.Idle
    }

 
     fun logout(context: Context, onLoggedOut: () -> Unit) {
         // Sign out from Firebase
         firebaseAuth.signOut()

         // Flag to track if Google sign-out is handled, to prevent double calling onLoggedOut
         var googleSignOutHandled = false

         // Try Google sign out if user is signed in with Google
         try {
             val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(context.getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build()

             val googleClient = GoogleSignIn.getClient(context, gso)

             // Check if there's a currently signed-in Google account
             // This prevents trying to sign out from Google if the user isn't logged in with Google
             if (GoogleSignIn.getLastSignedInAccount(context) != null) {
                 googleClient.signOut()
                     .addOnCompleteListener {
                         if (!googleSignOutHandled) {
                             googleSignOutHandled = true
                             onLoggedOut()
                         }
                     }
                     .addOnFailureListener {
                         // Log error if needed
                         Log.e("AuthViewModel", "Google sign-out failed: ${it.message}")
                         if (!googleSignOutHandled) {
                             googleSignOutHandled = true
                             onLoggedOut() // Still call onLoggedOut even on failure
                         }
                     }
             } else {
                 // No Google account signed in, so we can directly call onLoggedOut
                 if (!googleSignOutHandled) {
                     googleSignOutHandled = true
                     onLoggedOut()
                 }
             }
         } catch (e: Exception) {
             Log.e("AuthViewModel", "Error during Google sign-out attempt: ${e.message}")
             if (!googleSignOutHandled) {
                 googleSignOutHandled = true
                 onLoggedOut()
             }
         }
     }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Fields cannot be empty")
            return
        }

        _loginState.value = LoginState.Loading

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener

                    // Check if the user is in "admins" collection
                    firestore.collection("admins").document(uid).get()
                        .addOnSuccessListener { document ->
                            val userType = if (document.exists()) "admin" else "user"
                            _loginState.value = LoginState.Success(userType)
                        }
                        .addOnFailureListener {
                            _loginState.value = LoginState.Error("Failed to fetch user type")
                        }
                } else {
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }


    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState


    fun sendPasswordResetEmail(email: String) {
        if (email.isBlank()) {
            // Handle error, maybe update a StateFlow with error message
            _forgotPasswordState.value = ForgotPasswordState.Error("Email can't be empty")
            return
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _forgotPasswordState.value = ForgotPasswordState.Success("Reset email sent")
                } else {
                    _forgotPasswordState.value = ForgotPasswordState.Error(task.exception?.message ?: "Failed to send reset email")
                }
            }
    }

    fun resetForgotPasswordState() {
        _forgotPasswordState.value = ForgotPasswordState.Idle
    }



}
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userType: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    data class Success(val message: String) : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}

