package com.norbert.balazs.sliidechallangeapp.domain.use_case

import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.NewUserDto
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class CreateUserUseCase @Inject constructor(
    @Named("UserRepository") private val userRepository: UserRepository
) {

    companion object {
        private const val RESPONSE_SUCCESS = 201
    }

    suspend fun invoke(name: String, email: String, gender: String, status: String) = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.createUser(
                NewUserDto(
                    name,
                    email,
                    gender,
                    status
                )
            )
            if (response.isSuccessful && response.code() == RESPONSE_SUCCESS) {
                emit(Resource.Success(null))
            } else {
                emit(Resource.Error("Call succeeded but something went wrong!"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}