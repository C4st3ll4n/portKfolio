package br.com.dio.app.repositories.data.service

import br.com.dio.app.repositories.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    suspend fun  listRepos(@Path("user") user:String): List<Repo>
}