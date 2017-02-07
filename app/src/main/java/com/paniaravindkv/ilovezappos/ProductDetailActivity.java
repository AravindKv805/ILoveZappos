package com.paniaravindkv.ilovezappos;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.paniaravindkv.ilovezappos.databinding.ActivityProductDetailBinding;
import com.paniaravindkv.ilovezappos.helper.ImageDownloadTask;
import com.paniaravindkv.ilovezappos.model.Product;

/**
 * Created by Pani Aravind on 2/3/17.
 */
/**
 * <a href="https://icons8.com/web-app/31302/Add-Shopping-Cart">Add shopping cart icon credits</a>
 */

public class ProductDetailActivity extends AppCompatActivity {

    ImageDownloadTask task;
    Product product;
    ImageView productThumbnailImageView;
    TextView originalPriceTextView;
    TextView priceTextView;
    TextView percentOffTextView;
    MenuItem cartMenuItem;

    private int cartCount = 0;
    private android.support.v7.widget.ShareActionProvider shareActionProvider;

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

        cartMenuItem = menu.findItem(R.id.cartItem);
        cartMenuItem.setIcon(buildCounterDrawable(cartCount, R.drawable.ic_cart_menuitem));

        MenuItem shareMenuItem = menu.findItem(R.id.shareItem);
        shareMenuItem.setIcon(R.drawable.ic_share_menuitem);

        shareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);

        String productAsString = getIntent().getStringExtra("product");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "ilovezappos://open?productData=" + productAsString);
        shareActionProvider.setShareIntent(shareIntent);

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
            doAnimation(view);
        } else {
            Toast.makeText(getApplicationContext(), "Already in cart", Toast.LENGTH_SHORT).show();
        }

        invalidateOptionsMenu();
    }

    public void doAnimation(View view) {

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        view.setVisibility(View.VISIBLE);
        anim.start();

    }

}

