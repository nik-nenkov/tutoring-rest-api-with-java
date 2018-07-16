package jdbc.crud.demo;

import lombok.Data;

@Data
class Customer {
    private int id;
    private String name;

    Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer:{id:" + this.id + ",name:" + this.name + "}";
    }
}
