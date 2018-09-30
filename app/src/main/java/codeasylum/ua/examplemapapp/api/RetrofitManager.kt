package codeasylum.ua.examplemapapp.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private var retrofitApi: RetrofitApi? = null

    fun getClient(): RetrofitApi? {


        if (retrofitApi == null) {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            synchronized(Retrofit::class.java) {
                if (retrofitApi == null) {
                    val retrofit = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .baseUrl("https://www.google.com.ua/")
                            .build()
                    retrofitApi = retrofit.create(RetrofitApi::class.java)
                }
            }
        }
        return retrofitApi
    }
}