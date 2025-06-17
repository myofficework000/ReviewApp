package com.code4galaxy.reviewnow.model.data.repository.admin

import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AdminRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : IAdminRepository {

    override suspend fun register(user: User, password: String): UiState<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, password).await()
            val adminId = result.user?.uid ?: return UiState.Error("Admin ID not found")
            val newAdmin = user.copy(id = adminId, userType = "admin")
            firestore.collection("users").document(adminId).set(newAdmin).await()
            UiState.Success("Admin registered successfully")
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Registration failed")
        }
    }

    override suspend fun login(email: String, password: String): UiState<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val adminId = result.user?.uid ?: return UiState.Error("Admin ID not found")
            val snapshot = firestore.collection("users").document(adminId).get().await()
            if (!snapshot.exists()) return UiState.Error("Admin profile not found")
            val admin = snapshot.toObject(User::class.java)
                ?: return UiState.Error("Failed to parse admin data")
            if (admin.userType != "admin") return UiState.Error("Not an admin account")
            UiState.Success(admin)
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Login failed")
        }
    }

    override suspend fun logout(): UiState<String> {
        return try {
            auth.signOut()
            UiState.Success("Admin logged out")
        } catch (e: Exception) {
            UiState.Error("Logout failed")
        }
    }

    override fun getAdminData(adminId: String): Flow<UiState<User>> = callbackFlow {
        val listener = firestore.collection("users").document(adminId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(UiState.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val admin = snapshot?.toObject(User::class.java)
                if (admin != null && admin.userType == "admin") {
                    trySend(UiState.Success(admin))
                } else {
                    trySend(UiState.Error("Admin data not found"))
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun updateProfile(admin: User): UiState<String> {
        return try {
            firestore.collection("users").document(admin.id).set(admin).await()
            UiState.Success("Admin profile updated")
        } catch (e: Exception) {
            UiState.Error("Failed to update admin profile")
        }
    }

    override suspend fun addBrand(brand: Brand): UiState<String> {
        return try {
            val brandRef = firestore.collection("brands").document()
            val brandWithId = brand.copy(id = brandRef.id)
            brandRef.set(brandWithId).await()
            UiState.Success("Brand added")
        } catch (e: Exception) {
            UiState.Error("Failed to add brand")
        }
    }

    override fun getMyBrands(adminId: String): Flow<UiState<List<Brand>>> = callbackFlow {
        val listener = firestore.collection("brands")
            .whereEqualTo("adminId", adminId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(UiState.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val brands = snapshot?.toObjects(Brand::class.java).orEmpty()
                trySend(UiState.Success(brands))
            }
        awaitClose { listener.remove() }
    }

    override fun getBrandDetails(brandId: String): Flow<UiState<Brand>> = callbackFlow {
        val listener = firestore.collection("brands").document(brandId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(UiState.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val brand = snapshot?.toObject(Brand::class.java)
                if (brand != null) {
                    trySend(UiState.Success(brand))
                } else {
                    trySend(UiState.Error("Brand not found"))
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getReviewsForBrand(brandId: String): Flow<UiState<List<Review>>> = callbackFlow {
        val listener = firestore.collection("reviews")
            .whereEqualTo("brandId", brandId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(UiState.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val reviews = snapshot?.toObjects(Review::class.java).orEmpty()
                trySend(UiState.Success(reviews))
            }
        awaitClose { listener.remove() }
    }

    override fun getAllUsers(): Flow<UiState<List<User>>> = callbackFlow {
        val listener = firestore.collection("users")
            //.whereEqualTo("userType", "user") // Uncomment to exclude admins
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(UiState.Error(error.localizedMessage ?: "Error fetching users"))
                    return@addSnapshotListener
                }
                val users = snapshot?.toObjects(User::class.java).orEmpty()
                trySend(UiState.Success(users))
            }
        awaitClose { listener.remove() }
    }

    override fun suspendUser(userId: String): Flow<UiState<String>> = callbackFlow {
        firestore.collection("users").document(userId)
            .update("isSuspended", true)
            .addOnSuccessListener {
                trySend(UiState.Success("User suspended successfully"))
                close()
            }
            .addOnFailureListener { e ->
                trySend(UiState.Error(e.localizedMessage ?: "Failed to suspend user"))
                close()
            }
        awaitClose { /* no-op */ }
    }

}
