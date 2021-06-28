package com.pavelpotapov.guessthecelebrity.model.api;

import com.pavelpotapov.guessthecelebrity.entity.Celebrity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("ajax/list/data")
    Single<List<Celebrity>> getCelebrityList(
            @Query("year") int year,
            @Query("uri") String uri,
            @Query("type") String type
    );
}
