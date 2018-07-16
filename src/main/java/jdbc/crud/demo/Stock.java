package jdbc.crud.demo;

import lombok.Data;

@Data
class Stock {
    private int sockId;
    private float price;
    private int quantity;

    Stock(int sockId, float price, int quantity){
        this.sockId = sockId;
        this.price=price;
        this.quantity=quantity;
    }

}
