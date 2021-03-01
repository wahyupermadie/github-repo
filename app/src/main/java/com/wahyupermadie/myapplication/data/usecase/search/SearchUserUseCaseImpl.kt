package com.wahyupermadie.myapplication.data.usecase.search

import com.wahyupermadie.myapplication.data.repository.local.LocalRepository
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.utils.extension.transform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUserUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository
) : SearchUserUseCase {

    override suspend fun searchUsers(name: String, note: String): Flow<List<User>> {
        return localRepository.searchUsers(name, note).map {
            it.map {
                it.transform()
            }
        }
    }
}