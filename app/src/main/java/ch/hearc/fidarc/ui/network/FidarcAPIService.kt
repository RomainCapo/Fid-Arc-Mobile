package ch.hearc.fidarc.ui.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

import ch.hearc.fidarc.ui.data.model.Test

private const val BASE_URL = "https://jsonplaceholder.typicode.com/" //TODO : Remove this with the good api

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FidarcAPIService {

    @GET("/todos/{id}")
    fun getTest(@Path(value = "id") todoId: Int): Test
}

object FidarcAPI {
    val retrofitService : FidarcAPIService by lazy { retrofit.create(FidarcAPIService::class.java) }
}