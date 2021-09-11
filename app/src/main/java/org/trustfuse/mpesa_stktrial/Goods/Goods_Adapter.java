package org.trustfuse.mpesa_stktrial.Goods;

import com.google.firebase.firestore.Query;

public class Goods_Adapter {

    private String Category,Description,Name,image_uri,Price;

    public Goods_Adapter(){

    }
    public Goods_Adapter(String Category, String Description,String Name, String image_uri, String Price){
        this.Category = Category;
        this.Description = Description;
        this.Name = Name;
        this.image_uri = image_uri;
        this.Price = Price;
    }

//    public Goods_Adapter(String name, String Description, String category, String price, String image_uri) {
//        this.Category = category;
//        this.Description = Description;
//        this.Name = name;
//        this.image_uri = image_uri;
//        this.Price = price;
//    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

//    public String getCategory() {
//        return Category;
//    }
//
//    public void setCategory(String category) {
//        Category = category;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public String getImage_uri() {
//        return image_uri;
//    }
//
//    public void setImage_uri(String image_uri) {
//        this.image_uri = image_uri;
//    }
//
//    public String getPrice() {
//        return Price;
//    }
//
//    public void setPrice(String price) {
//        Price = price;
//    }
}