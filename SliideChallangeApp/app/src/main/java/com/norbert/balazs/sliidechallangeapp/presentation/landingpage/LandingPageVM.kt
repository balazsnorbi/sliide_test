package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import androidx.lifecycle.ViewModel
import com.norbert.balazs.sliidechallangeapp.domain.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LandingPageVM @Inject constructor(
    @Named("GetUsersUseCase") private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    suspend fun loadUsersAsync() = getUsersUseCase.invoke()
}