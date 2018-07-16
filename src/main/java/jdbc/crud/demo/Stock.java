package jdbc.crud.demo;

public class Stock {
    private int id;
    private float price;
    private int quantity;

    Stock(int id,float price, int quantity){
        this.id=id;
        this.price=price;
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
