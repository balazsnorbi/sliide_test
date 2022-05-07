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

    suspend fun invoke(name: String, email: String, gender: String, status: String) = flow {
        try {
            emit(Resource.Loading())
            userRepository.createUser(
                NewUserDto(
                    name,
                    email,
                    gender,
                    status
                )
            )
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}