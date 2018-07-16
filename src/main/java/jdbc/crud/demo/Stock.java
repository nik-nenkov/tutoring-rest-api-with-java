package jdbc.crud.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class Stock {
    @JsonProperty("stock_id")
    private int sockId;
    private float price;
    private int quantity;

    Stock(int sockId, float price, int quantity){
        this.sockId = sockId;
        this.price=price;
        this.quantity=quantity;
    }

}
