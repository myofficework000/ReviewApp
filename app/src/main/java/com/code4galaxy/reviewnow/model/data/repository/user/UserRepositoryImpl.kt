package com.code4galaxy.reviewnow.model.data.repository.user

import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    val auth:FirebaseAuth,
    val firestore: FirebaseFirestore
):IUserRepository{
    override suspend fun register(user: User, password: String): UiState<String> {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): UiState<User> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): UiState<String> {
        TODO("Not yet implemented")
    }

    override fun getUserData(userId: String): Flow<UiState<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(user: User): UiState<String> {
        TODO("Not yet implemented")
    }

    override fun getAllBrands(): Flow<UiState<List<Brand>>> {
        TODO("Not yet implemented")
    }

    override fun getBrandDetails(brandId: String): Flow<UiState<Brand>> {
        TODO("Not yet implemented")
    }

    override suspend fun submitReview(review: Review): UiState<String> {
        TODO("Not yet implemented")
    }

    override fun getMyReviews(userId: String): Flow<UiState<List<Review>>> {
        TODO("Not yet implemented")
    }

    override fun getReviewsForBrand(brandId: String): Flow<UiState<List<Review>>> {
        TODO("Not yet implemented")
    }
}