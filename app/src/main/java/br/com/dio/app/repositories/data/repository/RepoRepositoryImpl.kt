package br.com.dio.app.repositories.data.repository

import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.service.GithubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl(private val service: GithubService) : RepoRepository {

    override suspend fun listRepos(user: String) = flow {
        try {
            val repos = service.listRepos(user)
            emit(repos)

        } catch (e: HttpException) {
            throw RemoteException(e.message())
        }
    }
}