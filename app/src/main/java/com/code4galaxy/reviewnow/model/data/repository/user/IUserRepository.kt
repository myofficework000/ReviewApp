package com.code4galaxy.reviewnow.model.data.repository.user
import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    suspend fun register(user: User, password: String): UiState<String>

    suspend fun login(email: String, password: String): UiState<User>

    suspend fun logout(): UiState<String>

    fun getUserData(userId: String): Flow<UiState<User>>

    suspend fun updateProfile(user: User): UiState<String>

    fun getAverageRatingForBrand(brandId: String): Flow<UiState<Double>>

    fun getAllBrands(): Flow<UiState<List<Brand>>>

    fun getBrandDetails(brandId: String): Flow<UiState<Brand>>

    suspend fun submitReview(review: Review): UiState<String>

    fun getMyReviews(userId: String): Flow<UiState<List<Review>>>

    fun getReviewsForBrand(brandId: String): Flow<UiState<List<Review>>>
}
