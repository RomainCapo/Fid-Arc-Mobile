package ch.hearc.fidarc.ui.network

import ch.hearc.fidarc.ui.data.model.Company
import ch.hearc.fidarc.ui.data.model.CompanyCollection
import ch.hearc.fidarc.ui.data.model.FidelityCardCollection
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

import ch.hearc.fidarc.ui.data.model.Test
import retrofit2.Call

private const val BASE_URL = "https://fidarc.srvz-webapp.he-arc.ch/api/" //TODO : Remove this with the good api

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FidarcAPIService {

    @GET("/todos/{id}")
    suspend fun getTest(@Path(value = "id") todoId: Int): Test

    @GET("companies")
    suspend fun getCompaniesInfo(): CompanyCollection

    @GET("fidelityCards")
    suspend fun getFidelityCards(): FidelityCardCollection
}

object FidarcAPI {
    val retrofitService : FidarcAPIService by lazy { retrofit.create(FidarcAPIService::class.java) }
}