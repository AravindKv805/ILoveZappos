package com.paniaravindkv.ilovezappos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pani Aravind on 2/5/17.
 */

public class ProductsAPIResult {

    @SerializedName("originalTerm")
    @Expose
    private String originalTerm;

    @SerializedName("currentResultCount")
    @Expose
    private String currentResultCount;

    @SerializedName("totalResultCount")
    @Expose
    private String totalResultCount;

    @SerializedName("term")
    @Expose
    private String term;

    @SerializedName("results")
    @Expose
    private List<Product> products;

    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public String getOriginalTerm() {
        return originalTerm;
    }

    public void setOriginalTerm(String originalTerm) {
        this.originalTerm = originalTerm;
    }

    public String getCurrentResultCount() {
        return currentResultCount;
    }

    public void setCurrentResultCount(String currentResultCount) {
        this.currentResultCount = currentResultCount;
    }

    public String getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(String totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
