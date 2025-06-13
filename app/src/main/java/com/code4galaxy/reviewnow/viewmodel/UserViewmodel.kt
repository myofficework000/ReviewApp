package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import com.code4galaxy.reviewnow.model.data.repository.user.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewmodel @Inject constructor(
    val repository: IUserRepository
) : ViewModel() {


}