package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.reviewnow.model.Brand
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.User
import com.code4galaxy.reviewnow.model.data.repository.user.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: IUserRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val registerState: StateFlow<UiState<String>> = _registerState

    private val _loginState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val loginState: StateFlow<UiState<User>> = _loginState

    private val _userDataState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val userDataState: StateFlow<UiState<User>> = _userDataState

    private val _brandsState = MutableStateFlow<UiState<List<Brand>>>(UiState.Loading)
    val brandsState: StateFlow<UiState<List<Brand>>> = _brandsState

    private val _brandDetailState = MutableStateFlow<UiState<Brand>>(UiState.Loading)
    val brandDetailState: StateFlow<UiState<Brand>> = _brandDetailState

    private val _submitReviewState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val submitReviewState: StateFlow<UiState<String>> = _submitReviewState

    private val _myReviewsState = MutableStateFlow<UiState<List<Review>>>(UiState.Loading)
    val myReviewsState: StateFlow<UiState<List<Review>>> = _myReviewsState

    private val _reviewsForBrandState = MutableStateFlow<UiState<List<Review>>>(UiState.Loading)
    val reviewsForBrandState: StateFlow<UiState<List<Review>>> = _reviewsForBrandState

    private val _logoutState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val logoutState: StateFlow<UiState<String>> = _logoutState

    private val _updateProfileState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val updateProfileState: StateFlow<UiState<String>> = _updateProfileState

    private val _averageRatingState = MutableStateFlow<UiState<Double>>(UiState.Loading)
    val averageRatingState: StateFlow<UiState<Double>> = _averageRatingState


    fun register(user: User, password: String) = viewModelScope.launch {
        _registerState.value = UiState.Loading
        _registerState.value = repository.register(user, password)
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginState.value = UiState.Loading
        _loginState.value = repository.login(email, password)
    }

    fun getUserData(userId: String) = viewModelScope.launch {
        repository.getUserData(userId).collectLatest {
            _userDataState.value = it
        }
    }

    fun getAllBrands() = viewModelScope.launch {
        repository.getAllBrands().collectLatest {
            _brandsState.value = it
        }
    }

    fun getBrandDetails(brandId: String) = viewModelScope.launch {
        repository.getBrandDetails(brandId).collectLatest {
            _brandDetailState.value = it
        }
    }

    fun submitReview(review: Review) = viewModelScope.launch {
        _submitReviewState.value = UiState.Loading
        _submitReviewState.value = repository.submitReview(review)
    }

    fun getMyReviews(userId: String) = viewModelScope.launch {
        repository.getMyReviews(userId).collectLatest {
            _myReviewsState.value = it
        }
    }

    fun getReviewsForBrand(brandId: String) = viewModelScope.launch {
        repository.getReviewsForBrand(brandId).collectLatest {
            _reviewsForBrandState.value = it
        }
    }

    fun logout() = viewModelScope.launch {
        _logoutState.value = UiState.Loading
        _logoutState.value = repository.logout()
    }

    fun updateProfile(user: User) = viewModelScope.launch {
        _updateProfileState.value = UiState.Loading
        _updateProfileState.value = repository.updateProfile(user)
    }

    fun getAverageRatingForBrand(brandId: String) = viewModelScope.launch {
        repository.getAverageRatingForBrand(brandId).collectLatest {
            _averageRatingState.value = it
        }
    }

}
