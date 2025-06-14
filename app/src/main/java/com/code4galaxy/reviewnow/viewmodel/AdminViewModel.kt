package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User
import com.code4galaxy.reviewnow.model.data.repository.admin.IAdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: IAdminRepository
) : ViewModel() {

    // TODO (Ishak): seperate the viewmodel for clean code ...


    private val _registerState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val registerState: StateFlow<UiState<String>> = _registerState

    private val _loginState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val loginState: StateFlow<UiState<User>> = _loginState

    private val _logoutState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val logoutState: StateFlow<UiState<String>> = _logoutState

    private val _adminDataState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val adminDataState: StateFlow<UiState<User>> = _adminDataState

    private val _updateProfileState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val updateProfileState: StateFlow<UiState<String>> = _updateProfileState

    private val _addBrandState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addBrandState: StateFlow<UiState<String>> = _addBrandState

    private val _myBrandsState = MutableStateFlow<UiState<List<Brand>>>(UiState.Loading)
    val myBrandsState: StateFlow<UiState<List<Brand>>> = _myBrandsState

    private val _brandDetailsState = MutableStateFlow<UiState<Brand>>(UiState.Loading)
    val brandDetailsState: StateFlow<UiState<Brand>> = _brandDetailsState

    private val _reviewsForBrandState = MutableStateFlow<UiState<List<Review>>>(UiState.Loading)
    val reviewsForBrandState: StateFlow<UiState<List<Review>>> = _reviewsForBrandState


    fun register(user: User, password: String) = viewModelScope.launch {
        _registerState.value = UiState.Loading
        _registerState.value = repository.register(user, password)
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginState.value = UiState.Loading
        _loginState.value = repository.login(email, password)
    }

    fun logout() = viewModelScope.launch {
        _logoutState.value = UiState.Loading
        _logoutState.value = repository.logout()
    }

    fun getAdminData(adminId: String) = viewModelScope.launch {
        repository.getAdminData(adminId).collect {
            _adminDataState.value = it
        }
    }

    fun updateProfile(admin: User) = viewModelScope.launch {
        _updateProfileState.value = UiState.Loading
        _updateProfileState.value = repository.updateProfile(admin)
    }

    fun addBrand(brand: Brand) = viewModelScope.launch {
        _addBrandState.value = UiState.Loading
        _addBrandState.value = repository.addBrand(brand)
    }

    fun getMyBrands(adminId: String) = viewModelScope.launch {
        repository.getMyBrands(adminId).collect {
            _myBrandsState.value = it
        }
    }

    fun getBrandDetails(brandId: String) = viewModelScope.launch {
        repository.getBrandDetails(brandId).collect {
            _brandDetailsState.value = it
        }
    }

    fun getReviewsForBrand(brandId: String) = viewModelScope.launch {
        repository.getReviewsForBrand(brandId).collect {
            _reviewsForBrandState.value = it
        }
    }
}
