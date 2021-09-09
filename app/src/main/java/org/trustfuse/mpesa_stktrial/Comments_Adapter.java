package org.trustfuse.mpesa_stktrial;

public class Comments_Adapter {
    private String Commenter,Datee,Comment,Item_Name;
    private Comments_Adapter(){

    }
    private Comments_Adapter(String Commenter,String Datee,String Comment,String Item_Name){
        this.Comment = Comment;
        this.Commenter = Commenter;
        this.Datee = Datee;
        this.Item_Name = Item_Name;
    }

    public String getDatee() {
        return Datee;
    }

    public void setDatee(String datee) {
        Datee = datee;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCommenter() {
        return Commenter;
    }

    public void setCommenter(String commenter) {
        Commenter = commenter;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }
}
