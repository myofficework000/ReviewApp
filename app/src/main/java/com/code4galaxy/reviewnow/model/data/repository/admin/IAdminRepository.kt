package com.code4galaxy.reviewnow.model.data.repository.admin




import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User

import kotlinx.coroutines.flow.Flow

interface IAdminRepository {

    suspend fun register(user: User, password: String): UiState<String>

    suspend fun login(email: String, password: String): UiState<User>

    suspend fun logout(): UiState<String>

    fun getAdminData(adminId: String): Flow<UiState<User>>

    suspend fun updateProfile(admin: User): UiState<String>

    suspend fun addBrand(brand: Brand): UiState<String>

    fun getMyBrands(adminId: String): Flow<UiState<List<Brand>>>

    fun getBrandDetails(brandId: String): Flow<UiState<Brand>>

    fun getReviewsForBrand(brandId: String): Flow<UiState<List<Review>>>
}
