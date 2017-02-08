package com.paniaravindkv.ilovezappos.api;

import com.paniaravindkv.ilovezappos.model.ProductsAPIResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pani Aravind on 2/5/17.
 */

public interface ProductsAPI {

    @GET("/Search")
    Call<ProductsAPIResult> getProducts(@Query("term") String search, @Query("key") String api_key);    //  API to get search results

}
