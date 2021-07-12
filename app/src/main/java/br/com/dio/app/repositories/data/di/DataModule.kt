package br.com.dio.app.repositories.data.di

import android.util.Log
import br.com.dio.app.repositories.data.repository.RepoRepository
import br.com.dio.app.repositories.data.repository.RepoRepositoryImpl
import br.com.dio.app.repositories.data.service.GithubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private val TAG: String = "OkHTTP"

    fun load(){
        loadKoinModules(networkModules() + repositoryModule())
    }

    private fun networkModules(): Module{
        return module {
            single {
            val interceptor = HttpLoggingInterceptor{
                Log.v(TAG, it )
            }

                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<GithubService>(get(), get())
            }

        }
    }

    private fun repositoryModule():Module{
        return module {
            single<RepoRepository> {
                RepoRepositoryImpl(get())
            }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, converter: GsonConverterFactory): T{
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(converter)
            .build().create(T::class.java)
    }
}