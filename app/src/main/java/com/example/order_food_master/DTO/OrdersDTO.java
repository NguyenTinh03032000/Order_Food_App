package com.example.order_food_master.DTO;

import java.io.Serializable;

public class OrdersDTO implements Serializable {
    int id, employID, tableID;
    String status, dateOrder;

    public OrdersDTO() {
        this.id = 0;
        this.employID = 0;
        this.tableID =0;
        this.status = "";
        this. dateOrder = "";
    }
    public OrdersDTO(int id, int employID, int tableID, String status, String dateOrder) {
        this.id = id;
        this.employID = employID;
        this.tableID =tableID;
        this.status = status;
        this. dateOrder = dateOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployID() {
        return employID;
    }

    public void setEmployID(int employID) {
        this.employID = employID;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

}


