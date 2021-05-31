package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food_master.Adapter.AdapterPayment;
import com.example.order_food_master.DTO.PaymentDTO;
import com.example.order_food_master.DAO.DinTableDAO;
import com.example.order_food_master.DAO.OrdersDAO;
import java.util.List;

public class PaymentActivity extends AppCompatActivity  implements View.OnClickListener{

    GridView gvPayment;
    TextView tvSumPay;
    Button btPayment,btExit;
    AdapterPayment adapter;
    List<PaymentDTO> listPayment;
    OrdersDAO ordersDAO;
    DinTableDAO dinTableDAO;
    long sumPay = 0;
    int idOrder;
    int idTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        addControls();

        idTable = getIntent().getIntExtra("T_idTable",0);
        if(idTable != 0){
            setUpGridView();
            for(int i = 0; i < listPayment.size();i++){
                int price = (int) listPayment.get(i).getPrice();
                int quantity = (int) listPayment.get(i).getQuantity();
                sumPay += (quantity*price);
            }

            tvSumPay.setText("Tổng tiền"+": \t\t"+sumPay);
        }
    }
    public void setUpGridView(){
        idOrder = (int) ordersDAO.getIdOrderByIdTable(idTable,"false");
        listPayment = ordersDAO.getListFoodOrderWithTable(idOrder);
        adapter = new AdapterPayment(this,R.layout.custom_layout_payment,listPayment);
        gvPayment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void addControls() {
        gvPayment = (GridView) findViewById(R.id.gv_listPayment);
        tvSumPay = (TextView) findViewById(R.id.tv_sumPay);
        btPayment = (Button) findViewById(R.id.bt_payment);
        btExit = (Button) findViewById(R.id.bt_exitPay);

        btPayment.setOnClickListener(this);
        btExit.setOnClickListener(this);
        ordersDAO = new OrdersDAO(this);
        dinTableDAO = new DinTableDAO(this);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_payment:
                boolean rsTable = dinTableDAO.setStatusTableById(idTable,"false");
                boolean rsOrder = ordersDAO.updateStatusOrderWithTable(idTable,"true");
                if(rsTable && rsOrder){
                    Toast.makeText(this,"Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    setUpGridView();
                    tvSumPay.setText("Tổng tiền"+":");
                } else {
                    Toast.makeText(this,"Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_exitPay:
                finish();
                break;
        }
    }
}