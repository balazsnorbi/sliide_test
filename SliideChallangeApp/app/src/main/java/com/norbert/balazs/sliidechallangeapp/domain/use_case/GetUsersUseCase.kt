package com.norbert.balazs.sliidechallangeapp.domain.use_case

import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.data.remote.dto.toUser
import com.norbert.balazs.sliidechallangeapp.domain.model.User
import com.norbert.balazs.sliidechallangeapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class GetUsersUseCase @Inject constructor(
    @Named("UserRepository") private val userRepository: UserRepository
) {
    suspend fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val users = mutableListOf<User>()
            userRepository.getUsers().forEach {
                users.add(it.toUser())

            }
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong while fetching favorites!"))
        }
    }
}