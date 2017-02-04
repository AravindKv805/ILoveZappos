package com.paniaravindkv.ilovezappos;

/**
 * Created by Pani Aravind on 2/3/17.
 */

public class Product {

    String productName;
    String productId;
    String brandName;
    String originalPrice;
    String price;
    String percentOff;
    String productUrl;
    String thumbnailImageUrl;
    String styleId;
    String colorId;

    public Product(String productName, String productId, String brandName, String originalPrice, String price, String percentOff, String productUrl, String thumbnailImageUrl, String styleId, String colorId) {
        this.productName = productName;
        this.productId = productId;
        this.brandName = brandName;
        this.originalPrice = originalPrice;
        this.price = price;
        this.percentOff = percentOff;
        this.productUrl = productUrl;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.styleId = styleId;
        this.colorId = colorId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(String percentOff) {
        this.percentOff = percentOff;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

}
