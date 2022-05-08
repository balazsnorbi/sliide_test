package com.norbert.balazs.sliidechallangeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norbert.balazs.sliidechallangeapp.common.DispatcherProvider
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.domain.model.User
import com.norbert.balazs.sliidechallangeapp.domain.use_case.CreateUserUseCase
import com.norbert.balazs.sliidechallangeapp.domain.use_case.DeleteUserUseCase
import com.norbert.balazs.sliidechallangeapp.domain.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("DispatcherProvider") private val dispatcherProvider: DispatcherProvider,
    @Named("GetUsersUseCase") private val getUsersUseCase: GetUsersUseCase,
    @Named("CreateUserUseCase") private val createUserUseCase: CreateUserUseCase,
    @Named("DeleteUserUseCase") private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList = _usersList.asStateFlow()

    private val _isFailed = MutableStateFlow(false)
    val isFailed = _isFailed.asStateFlow()

    init {
        loadUsersAsync()
    }

    fun loadUsersAsync() {
        viewModelScope.launch {
            getUsersUseCase.invoke().flowOn(dispatcherProvider.io).collect {
                when (it) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                        _isFailed.value = false
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        it.data?.let { users ->
                            _usersList.value = users
                        }
                        _isFailed.value = false
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _usersList.value = emptyList()
                        _isFailed.value = true
                    }
                }
            }
        }
    }

    suspend fun createUser(name: String, email: String, gender: String, status: String) =
        createUserUseCase.invoke(name, email, gender, status).flowOn(dispatcherProvider.io)

    suspend fun deleteUser(userId: Int) =
        deleteUserUseCase.invoke(userId).flowOn(dispatcherProvider.io)
}