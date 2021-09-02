package org.trustfuse.mpesa_stktrial;

public class Cart_Adapter {
    private String Category,Name,Image,Price;
    private  Cart_Adapter(){

    }
    private Cart_Adapter(String Category,String Name,String Image,String Price){
        this.Category = Category;
        this.Image = Image;
        this.Name = Name;
        this.Price = Price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
