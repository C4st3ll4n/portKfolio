package br.com.dio.app.repositories.data.repository

import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.service.GithubService
import kotlinx.coroutines.flow.flow

class RepoRepositoryImpl(private val service: GithubService): RepoRepository {

    override suspend fun listRepos(user: String) = flow {
        val repos =  service.listRepos(user)
        emit(repos)
    }
}