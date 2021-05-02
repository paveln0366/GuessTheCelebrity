package com.pavelpotapov.guessthecelebrity.model.api

import com.pavelpotapov.guessthecelebrity.model.database.entity.Celebrity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val QUERY_PARAM_YEAR = "year"
        private const val QUERY_PARAM_URI = "uri"
        private const val QUERY_PARAM_TYPE = "type"
    }

    @GET("ajax/list/data")
    fun getCelebrityList(
            @Query(QUERY_PARAM_YEAR) year: Int = 2020,
            @Query(QUERY_PARAM_URI) uri: String = "celebrities",
            @Query(QUERY_PARAM_TYPE) type: String = "person"
    ): Single<List<Celebrity>>
}