package th.kaowkaow.pokedexandroid.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val gson: Gson = Gson().newBuilder()
        .setLenient()
        .setPrettyPrinting()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttp)
        .baseUrl("https://run.mocky.io/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> createService(mService: Class<T>): T {
        return retrofit.create(mService)
    }
}