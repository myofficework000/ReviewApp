package com.code4galaxy.reviewnow.model.data.repository.user


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


class UserRepositoryImpl(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) : IUserRepository {
    override suspend fun register(user: User, password: String): UiState<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, password).await()
            val userId = result.user?.uid ?: return UiState.Error("User ID not found")

            val newUser = user.copy(id = userId)
            firestore.collection("users")
                .document(userId)
                .set(newUser)
                .await()


            UiState.Success("Registration successful")
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun login(email: String, password: String): UiState<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return UiState.Error("User ID not found")


            val snapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            if (!snapshot.exists()) {
                return UiState.Error("User profile not found")
            }


            val user = snapshot.toObject(User::class.java)
                ?: return UiState.Error("Failed to parse user data")


            UiState.Success(user)
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Login failed")
        }
    }


    override suspend fun logout(): UiState<String> {
        return try {
            auth.signOut()
            UiState.Success("Logout successful")
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Logout failed")
        }
    }


    override fun getUserData(userId: String): Flow<UiState<User>> = callbackFlow {
        val listener =
            firestore.collection("users").document(userId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(UiState.Error(e.localizedMessage ?: "Error fetching user"))
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val user = snapshot.toObject(User::class.java)
                    if (user != null) {
                        trySend(UiState.Success(user))
                    } else {
                        trySend(UiState.Error("User data is null"))
                    }
                } else {
                    trySend(UiState.Error("User not found"))
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun updateProfile(user: User): UiState<String> {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            UiState.Success("Profile updated successfully")
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Profile update failed")
        }
    }

    override fun getAllBrands(): Flow<UiState<List<Brand>>> = callbackFlow {
        val listener = firestore.collection("brands").addSnapshotListener { snapshot, e ->
            if (e != null) {
                trySend(UiState.Error(e.localizedMessage ?: "Error fetching brands"))
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val brands = snapshot.documents.mapNotNull { it.toObject(Brand::class.java) }
                trySend(UiState.Success(brands))
            } else {
                trySend(UiState.Error("No brands found"))
            }
        }
        awaitClose { listener.remove() }
    }

    override fun getBrandDetails(brandId: String): Flow<UiState<Brand>> = callbackFlow {
        val listener =
            firestore.collection("brands").document(brandId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(UiState.Error(e.localizedMessage ?: "Error fetching brand"))
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val brand = snapshot.toObject(Brand::class.java)
                    if (brand != null) {
                        trySend(UiState.Success(brand))
                    } else {
                        trySend(UiState.Error("Brand data is null"))
                    }
                } else {
                    trySend(UiState.Error("Brand not found"))
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun submitReview(review: Review): UiState<String> {
        return try {
            val newDocRef = firestore.collection("reviews").document()
            val newReview = review.copy(id = newDocRef.id)
            newDocRef.set(newReview).await()
            UiState.Success("Review submitted successfully")
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Failed to submit review")
        }
    }

    override fun getMyReviews(userId: String): Flow<UiState<List<Review>>> = callbackFlow {
        val listener = firestore.collection("reviews").whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(UiState.Error(e.localizedMessage ?: "Error fetching reviews"))
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reviews = snapshot.documents.mapNotNull { it.toObject(Review::class.java) }
                    trySend(UiState.Success(reviews))
                } else {
                    trySend(UiState.Error("No reviews found"))
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getAverageRatingForBrand(brandId: String): Flow<UiState<Double>> = callbackFlow {
        val listener = firestore.collection("reviews")
            .whereEqualTo("brandId", brandId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(UiState.Error(e.localizedMessage ?: "Error fetching reviews"))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val ratings = snapshot.documents.mapNotNull {
                        it.toObject(Review::class.java)?.rating?.toDouble()
                    }

                    val average = if (ratings.isNotEmpty()) ratings.average() else 0.0
                    trySend(UiState.Success(average))
                } else {
                    trySend(UiState.Error("No reviews found"))
                }
            }

        awaitClose { listener.remove() }
    }

    override fun getReviewsForBrand(brandId: String): Flow<UiState<List<Review>>> = callbackFlow {
        val listener = firestore.collection("reviews").whereEqualTo("brandId", brandId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(UiState.Error(e.localizedMessage ?: "Error fetching reviews"))
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reviews = snapshot.documents.mapNotNull { it.toObject(Review::class.java) }
                    trySend(UiState.Success(reviews))
                } else {
                    trySend(UiState.Error("No reviews found"))
                }
            }
        awaitClose { listener.remove() }
    }
}


