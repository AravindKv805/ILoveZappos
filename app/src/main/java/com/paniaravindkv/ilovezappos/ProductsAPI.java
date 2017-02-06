package com.paniaravindkv.ilovezappos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pani Aravind on 2/5/17.
 */

public interface ProductsAPI {

    @GET("/Search")
    Call<ProductsAPIResult> getProducts(@Query("term") String search, @Query("key") String api_key);

}
