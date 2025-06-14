package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.data.repository.user.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _submitReviewState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val submitReviewState: StateFlow<UiState<String>> = _submitReviewState

    private val _brandReviews = MutableStateFlow<UiState<List<Review>>>(UiState.Loading)
    val brandReviews: StateFlow<UiState<List<Review>>> = _brandReviews

    fun submitReview(review: Review) {
        viewModelScope.launch {
            _submitReviewState.value = UiState.Loading
            _submitReviewState.value = userRepository.submitReview(review)
        }
    }

    fun getReviewsForBrand(brandId: String) {
        viewModelScope.launch {
            userRepository.getReviewsForBrand(brandId)
                .collect { _brandReviews.value = it }
        }
    }
}