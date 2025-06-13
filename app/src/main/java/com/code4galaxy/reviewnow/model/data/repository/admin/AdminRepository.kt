package com.code4galaxy.reviewnow.model.data.repository.admin

import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User
import kotlinx.coroutines.flow.Flow

class AdminRepository():IAdminRepository {
    override suspend fun register(user: User, password: String): UiState<String> {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): UiState<User> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): UiState<String> {
        TODO("Not yet implemented")
    }

    override fun getAdminData(adminId: String): Flow<UiState<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(admin: User): UiState<String> {
        TODO("Not yet implemented")
    }

    override suspend fun addBrand(brand: Brand): UiState<String> {
        TODO("Not yet implemented")
    }

    override fun getMyBrands(adminId: String): Flow<UiState<List<Brand>>> {
        TODO("Not yet implemented")
    }

    override fun getBrandDetails(brandId: String): Flow<UiState<Brand>> {
        TODO("Not yet implemented")
    }

    override fun getReviewsForBrand(brandId: String): Flow<UiState<List<Review>>> {
        TODO("Not yet implemented")
    }
}