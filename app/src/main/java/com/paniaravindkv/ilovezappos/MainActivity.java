package com.paniaravindkv.ilovezappos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText searchEditText;

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
    }
}
