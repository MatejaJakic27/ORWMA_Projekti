package hr.ferit.matejajakic.mindgarden

import retrofit2.Call
import retrofit2.http.GET

interface FakerEndpoints {
    @GET("/raw/6EeUPcJM")
    fun getBooks(): Call<ArrayList<BookJSON>>
}