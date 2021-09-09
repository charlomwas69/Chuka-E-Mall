package org.trustfuse.mpesa_stktrial.Cart;

public class Cart_Adapter {
    private String Category,Name,Image,Price,Qty;
    private  Cart_Adapter(){

    }
    private Cart_Adapter(String Category,String Name,String Image,String Price,String Qty){
        this.Category = Category;
        this.Image = Image;
        this.Name = Name;
        this.Price = Price;
        this.Qty = Qty;
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

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
