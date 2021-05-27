package com.example.order_food_master.DTO;

import java.io.Serializable;

public class PaymentDTO implements Serializable {
    String nameFood;
    long quantity, price;

    public PaymentDTO() {
        String namFood = "";
        int quantity = 0;
        long price = 0;
    }

    public PaymentDTO(String nameFood, int quantity, int price){
        this.nameFood = nameFood;
        this.quantity = quantity;
        this.price = price;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
