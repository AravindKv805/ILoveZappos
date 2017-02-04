package com.paniaravindkv.ilovezappos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

            if (resultInJSON.getString("currentResultCount").equals("0")) {

                Toast.makeText(getApplicationContext(), "No results found!!", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
