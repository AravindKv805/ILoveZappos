package com.paniaravindkv.ilovezappos;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import com.paniaravindkv.ilovezappos.databinding.ActivityProductDetailBinding;

/**
 * Created by Pani Aravind on 2/3/17.
 */

public class ProductDetailActivity extends Activity {

    ImageDownloadTask task;
    Product product;
    ImageView productThumbnailImageView;
    TextView originalPriceTextView;
    TextView priceTextView;
    TextView percentOffTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        task = new ImageDownloadTask();

        Bitmap downloadedImage;

        ActivityProductDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        try {
            JSONObject productInJSON = new JSONObject(getIntent().getStringExtra("productInJSON"));

            product = new Product(productInJSON.getString("productName"),
                    productInJSON.getString("productId"),
                    productInJSON.getString("brandName"),
                    productInJSON.getString("originalPrice"),
                    productInJSON.getString("price"),
                    productInJSON.getString("percentOff"),
                    productInJSON.getString("productUrl"),
                    productInJSON.getString("thumbnailImageUrl"),
                    productInJSON.getString("styleId"),
                    productInJSON.getString("colorId")
            );

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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
