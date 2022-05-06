package com.norbert.balazs.sliidechallangeapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norbert.balazs.sliidechallangeapp.common.APPLICATION_TAG
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.common.SPLASH_SCREEN_DURATION
import com.norbert.balazs.sliidechallangeapp.domain.model.User
import com.norbert.balazs.sliidechallangeapp.domain.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("GetUsersUseCase") private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _isStarting = MutableStateFlow(true)
    val isStarting = _isStarting.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList = _usersList.asStateFlow()

    private val _isFailed = MutableStateFlow(false)
    val isFailed = _isFailed.asStateFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            _isStarting.value = false
        }
        loadUsersAsync()
    }

    fun loadUsersAsync() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> {
                        Log.i(APPLICATION_TAG, "Loading ...")
                        _isLoading.value = true
                        _isFailed.value = false
                    }
                    is Resource.Success -> {
                        Log.i(APPLICATION_TAG, "Success!")
                        _isLoading.value = false
                        it.data?.let { users ->
                            _usersList.value = users
                        }
                        _isFailed.value = false
                    }
                    is Resource.Error -> {
                        Log.i(APPLICATION_TAG, "Error!")
                        _isLoading.value = false
                        _usersList.value = emptyList()
                        _isFailed.value = true
                    }
                }
            }
        }
    }
}