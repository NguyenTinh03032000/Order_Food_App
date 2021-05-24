package com.example.order_food_master.DTO;

import java.io.Serializable;

public class FoodTypeDTO implements Serializable {
    int id;
    String name;

    public void FoodTypeDTO(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
