package com.paniaravindkv.ilovezappos;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pani Aravind on 2/3/17.
 */

/**
 * <a href="https://icons8.com/web-app/31302/Add-Shopping-Cart">Add shopping cart icon credits</a>
 */

public class MainActivity extends AppCompatActivity {

    EditText searchEditText;
    ProductsAPI productsAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View bgImage = findViewById(R.id.activity_main);
        Drawable background = bgImage.getBackground();
        background.setAlpha(100);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchEditText.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchEditText.setText("");
    }

    public void searchAction(View view) {

        String searchText = searchEditText.getText().toString();

        Log.i("INFO", "Search Text - " + searchText);

        if (searchText.equals("")) {
            invalidTextToast();
            return;
        }
        String apiKey = getString(R.string.api_key);

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        productsAPI = retrofit.create(ProductsAPI.class);

        Call<ProductsAPIResult> productsAPIResult = productsAPI.getProducts(searchText, apiKey);

        productsAPIResult.enqueue(new Callback<ProductsAPIResult>() {
            @Override
            public void onResponse(Call<ProductsAPIResult> call, Response<ProductsAPIResult> response) {

                Log.i("INFO", "Response - " + response.body().getCurrentResultCount());

                if (response.body().getCurrentResultCount().equals("0")) {

                    noResultsToast();
                } else {

                    Gson gs = new Gson();
                    String firstResult = gs.toJson(response.body().getProducts().get(0));

                    if (firstResult != null) {
                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                        intent.putExtra("product", firstResult);
                        startActivity(intent);
                    } else {
                        noResultsToast();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsAPIResult> call, Throwable t) {
                Log.i("ERROR", t.getMessage());
                errorToast();
            }
        });

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    public void noResultsToast() {
        Toast.makeText(getApplicationContext(), "No results found!!", Toast.LENGTH_LONG).show();
    }

    public void invalidTextToast() {
        Toast.makeText(getApplicationContext(), "Please enter a valid text!!", Toast.LENGTH_SHORT).show();
    }

    public void errorToast() {
        Toast.makeText(getApplicationContext(), "Could not complete search!!", Toast.LENGTH_LONG).show();
    }
}
