package com.minerva.testrecyclerdrag.model;

/**
 * Created by Abdulrahman on 9/5/2018.
 */

public class Order {

    private String   ProductId;
    private String   ProductName;

    private String   ProductImage;

    public Order() {
    }

    public Order(String productId, String productName, String productImage) {
        ProductId = productId;
        ProductName = productName;
        ProductImage=productImage;

    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }



    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }
}
