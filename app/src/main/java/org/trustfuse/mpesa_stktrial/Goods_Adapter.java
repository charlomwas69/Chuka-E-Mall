package org.trustfuse.mpesa_stktrial;

import com.google.firebase.firestore.Query;

public class Goods_Adapter {

    private String Category,Description,Name,image_uri;
    private Long Price;

    private Goods_Adapter(){

    }
    private Goods_Adapter(String Categoty, String Description,String Name, String image_uri, long Price){
        this.Category = Categoty;
        this.Description = Description;
        this.Name = Name;
        this.image_uri = image_uri;
        this.Price = Price;
    }

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

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }
}
