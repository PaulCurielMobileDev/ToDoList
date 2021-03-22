package mexicandeveloper.com.todolist.repository

import mexicandeveloper.com.todolist.api.ApiTodoListService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "http://jsonplaceholder.typicode.com/"

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService : ApiTodoListService = getRetrofit().create(ApiTodoListService::class.java)
}