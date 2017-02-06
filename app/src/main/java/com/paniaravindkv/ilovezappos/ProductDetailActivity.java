package com.paniaravindkv.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.paniaravindkv.ilovezappos.databinding.ActivityProductDetailBinding;

/**
 * Created by Pani Aravind on 2/3/17.
 */

public class ProductDetailActivity extends AppCompatActivity {

    ImageDownloadTask task;
    Product product;
    ImageView productThumbnailImageView;
    TextView originalPriceTextView;
    TextView priceTextView;
    TextView percentOffTextView;

    private int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        task = new ImageDownloadTask();

        Bitmap downloadedImage;

        ActivityProductDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        try {

            Gson gs = new Gson();
            String productAsString = getIntent().getStringExtra("product");

            product = gs.fromJson(productAsString, Product.class);

            downloadedImage = task.execute(product.getThumbnailImageUrl()).get();
            if (downloadedImage != null) {
                productThumbnailImageView = (ImageView) findViewById(R.id.productThumbnailImageView);
                productThumbnailImageView.setImageBitmap(downloadedImage);
            }

            originalPriceTextView = (TextView) findViewById(R.id.originalPriceTextView);
            priceTextView = (TextView) findViewById(R.id.priceTextView);
            percentOffTextView = (TextView) findViewById(R.id.percentOffTextView);

            if (product.getOriginalPrice().equals(product.getPrice())) {
                originalPriceTextView.setVisibility(View.GONE);
                percentOffTextView.setVisibility(View.GONE);
            } else {
                originalPriceTextView.setPaintFlags(originalPriceTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }

            binding.setProduct(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.cartItem);
        menuItem.setIcon(buildCounterDrawable(cartCount, R.drawable.ic_cart_menuitem));

        return true;
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.cart_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.cartMenuLayout);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.cartCounter);
            textView.setText("" + cartCount);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    public void addToCart(View view) {

        Log.i("INFO", "add to cart clicked");

        if (cartCount == 0) {
            cartCount++;
            Toast.makeText(getApplicationContext(), "Added to Cart!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already in cart", Toast.LENGTH_SHORT).show();
        }

        invalidateOptionsMenu();
    }

}

