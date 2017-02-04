package com.paniaravindkv.ilovezappos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Pani Aravind on 2/3/17.
 */

public class MainActivity extends Activity {

    EditText searchEditText;
    DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View bgImage = findViewById(R.id.activity_main);
        Drawable background = bgImage.getBackground();
        background.setAlpha(100);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
    }

    public void searchAction(View view) {

        String searchText = searchEditText.getText().toString();

        Log.i("INFO", "Search Button Tapped");
        Log.i("INFO", "Search Text - " + searchText);

        downloadTask = new DownloadTask();

        String baseUrl = getString(R.string.api_base_url);
        String apiKey = getString(R.string.api_key);

        String urlString = baseUrl + "?term=" + searchText + "&key=" + apiKey;

        String result = "";

        Log.i("INFO", "Search API URL - " + urlString);

        try {
            result = downloadTask.execute(urlString).get();

            Log.i("INFO", "API call result - " + result);

            JSONObject resultInJSON = new JSONObject(result);

            Log.i("INFO", "API call result count - " + resultInJSON.getString("currentResultCount"));

            if (isEmptyResults(resultInJSON)) {

                noResultsToast();
            } else {
                JSONObject firstResult = getFirstResult(resultInJSON);

                if (firstResult != null) {
                    Log.i("INFO", "firstResult - " + firstResult);

                    Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                    startActivity(intent);
                } else {
                    noResultsToast();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            errorToast();
        } catch (ExecutionException e) {
            e.printStackTrace();
            errorToast();
        } catch (JSONException e) {
            e.printStackTrace();
            errorToast();
        }

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    public boolean isEmptyResults(JSONObject resultInJSON) {
        try {
            return resultInJSON.getString("currentResultCount").equals("0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public JSONObject getFirstResult(JSONObject resultInJSON) {

        try {

            JSONArray resultInJSONArray = (JSONArray) resultInJSON.get("results");
            return (JSONObject) resultInJSONArray.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void noResultsToast() {
        Toast.makeText(getApplicationContext(), "No results found!!", Toast.LENGTH_LONG).show();
    }

    public void errorToast() {
        Toast.makeText(getApplicationContext(), "Could not complete search!!", Toast.LENGTH_LONG).show();
    }
}
