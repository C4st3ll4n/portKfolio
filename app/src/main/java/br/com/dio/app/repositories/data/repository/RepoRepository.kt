package br.com.dio.app.repositories.data.repository

import br.com.dio.app.repositories.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun listRepos(user:String): Flow<List<Repo>>
}