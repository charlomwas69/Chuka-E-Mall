package org.trustfuse.mpesa_stktrial.Categories;
import com.google.firebase.firestore.Query;
public class Categories_Adapter {
    private String Category, Image;


    private Categories_Adapter(){

    }
    private Categories_Adapter(String Category,String Image){
        this.Category = Category;
        this.Image = Image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
