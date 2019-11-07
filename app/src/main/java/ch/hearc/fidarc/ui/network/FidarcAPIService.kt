package ch.hearc.fidarc.ui.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import ch.hearc.fidarc.ui.data.model.Token
import ch.hearc.fidarc.ui.data.model.User
import retrofit2.Response
import retrofit2.http.*

private const val BASE_URL = "https://fidarc.srvz-webapp.he-arc.ch/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FidarcAPIService {

    @GET("/api/user")
    suspend fun getUser(@Header("authorization") token: String): Response<User>

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun login(  @Field("grant_type") grantType: String,
                        @Field("client_id") clientId: Int,
                        @Field("client_secret") clientSecret: String,
                        @Field("username") username: String,
                        @Field("password") password: String): Response<Token>
}

object FidarcAPI {
    val retrofitService : FidarcAPIService by lazy { retrofit.create(FidarcAPIService::class.java) }
}