package com.paniaravindkv.ilovezappos;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Pani Aravind on 2/3/17.
 */

public class ProductDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        View bgImage = findViewById(R.id.activity_product_detail);
        Drawable background = bgImage.getBackground();
        background.setAlpha(80);
    }

}
