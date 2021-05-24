package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.order_food_master.DAO.FoodTypeDAO;
public class InsertFoodType extends AppCompatActivity implements View.OnClickListener{
    EditText etName;
    Button btInsert;
    FoodTypeDAO foodTypeDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food_type);
        addControls();
    }
    private void addControls() {
        etName = (EditText) findViewById(R.id.et_insert_foodtypeName);
        btInsert = (Button) findViewById(R.id.bt_insertFoodType);

        btInsert.setOnClickListener(this);
        foodTypeDAO = new FoodTypeDAO(this);
    }
    @Override
    public void onClick(View view) {
        String name = etName.getText().toString();
        if(name != null || name.equals("")){
            boolean rs = foodTypeDAO.insert(name);
            Intent intent = new Intent();
            intent.putExtra("Result_insertFoodType",rs);
            setResult(Activity.RESULT_OK,intent);
            finish();
        } else{
            Toast.makeText(this,"Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}