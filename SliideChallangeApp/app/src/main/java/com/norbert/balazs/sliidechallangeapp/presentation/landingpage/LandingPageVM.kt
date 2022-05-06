package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norbert.balazs.sliidechallangeapp.common.APPLICATION_TAG
import com.norbert.balazs.sliidechallangeapp.data.remote.UsersDbApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LandingPageVM @Inject constructor(
    @Named("UsersDbApi") private val usersDbApi: UsersDbApi
) : ViewModel() {

    fun getUsers() {
        viewModelScope.launch {
            val users = usersDbApi.getUsers()
            Log.i(APPLICATION_TAG, "Users count: ${users.size}")
        }
    }
}