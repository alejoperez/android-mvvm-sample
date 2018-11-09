package com.mvvm.sample.webservice

import android.content.Context
import com.mvvm.sample.BuildConfig
import com.mvvm.sample.livedata.LiveDataCallAdapterFactory
import com.mvvm.sample.data.preference.PreferenceManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WebService {

    enum class AuthenticationType {
        BASIC, OAUTH, NONE
    }

    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 60L


    private val httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    private val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())

    fun <S> createService(context: Context, serviceClass: Class<S>, authType: AuthenticationType = AuthenticationType.NONE): S {

        val requestHeaders = getHeaders(context, authType)

        httpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                    for (entry in requestHeaders.entries) {
                        requestBuilder.addHeader(entry.key, entry.value)
                    }
                    requestBuilder.method(original.method(), original.body())
                    chain.proceed(requestBuilder.build())
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return builder.client(httpClientBuilder.build()).build().create(serviceClass)
    }

    private fun getHeaders(context: Context, authType: AuthenticationType): Map<String, String> {
        val requestHeader = hashMapOf(
                "version" to BuildConfig.VERSION_NAME
        )
        val accessToken = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"")
        when (authType) {
            AuthenticationType.BASIC -> {
                requestHeader["Authorization"] = "Basic $accessToken"
            }
            AuthenticationType.OAUTH -> {
                requestHeader["Authorization"] = "Bearer $accessToken"
            }
            AuthenticationType.NONE -> Unit
        }
        return requestHeader
    }



}


