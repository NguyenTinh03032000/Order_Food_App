package com.example.order_food_master.DTO;

import java.io.Serializable;

public class DinTableDTO implements Serializable {
    int id;
    String name;
    boolean isChoose;

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

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public DinTableDTO() {
        this.id = id;
        this.name = name;
        this.isChoose = isChoose;
    }
}
