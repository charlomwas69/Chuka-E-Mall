package org.trustfuse.mpesa_stktrial.Orders;

public class Order_Adapter {
    private String Category,Image,Name,Price,Status;

    private Order_Adapter(){

    }
    private Order_Adapter(String Category, String Image,String Name,String Price, String Status){
        this.Category = Category;
        this.Image = Image;
        this.Name = Name;
        this.Price = Price;
        this.Status = Status;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
