package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.order_food_master.DAO.OrdersDAO;
import com.example.order_food_master.DTO.OrderDetailDTO;

public class FoodQuantityActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etQuantity;
    Button btInsert;
    int idTable, idFood;
    OrdersDAO ordersDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_quantity);

        Intent intent = getIntent();
        idTable = intent.getIntExtra("T_idTable",0);
        idFood = intent.getIntExtra("T_idFood",0);
        addControls();
    }
    private void addControls() {
        etQuantity = (EditText) findViewById(R.id.et_insert_quantityFood);
        btInsert = (Button) findViewById(R.id.bt_insertQuantity);
        btInsert.setOnClickListener(this);
        ordersDAO = new OrdersDAO(this);
    }
    @Override
    public void onClick(View view) {
        int idOrder = (int) ordersDAO.getIdOrderByIdTable(idTable, "false");
        boolean rs = ordersDAO.checkFoodHasExist(idOrder,idFood);
        if(rs){
            // update order detail
            int quantityOld = ordersDAO.getQuantityFoodByOrder(idOrder,idFood);
            int quantityNew = Integer.parseInt(etQuantity.getText().toString());

            int sum = quantityOld + quantityNew;

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setIdOrder(idOrder);
            orderDetailDTO.setIdFood(idFood);
            orderDetailDTO.setQuantity(sum);

            boolean result = ordersDAO.updateQuantity(orderDetailDTO);
            if (result){
                Toast.makeText(this,"Thêm thành công", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this,"Thêm thất bại", Toast.LENGTH_SHORT).show();
            }

        }else{
            // insert order detail
            int quantity = Integer.parseInt(etQuantity.getText().toString());

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setIdOrder(idOrder);
            orderDetailDTO.setIdFood(idFood);
            orderDetailDTO.setQuantity(quantity);

            boolean result = ordersDAO.insertDetail(orderDetailDTO);
            if (result){
                Toast.makeText(this,"Thêm thành công", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this,"Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}