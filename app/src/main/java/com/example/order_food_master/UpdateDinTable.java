package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.order_food_master.DAO.DinTableDAO;

public class UpdateDinTable extends AppCompatActivity implements View.OnClickListener{
    EditText etTableName;
    Button btUpdate;
    DinTableDAO dinTableDAO;
    int idTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_din_table);
        (UpdateDinTable.this).getSupportActionBar().setTitle("Cập nhật");
        addControls();
        idTable = getIntent().getIntExtra("T_idTable",0);
    }
    private void addControls() {
        etTableName = (EditText) findViewById(R.id.et_update_tableName);
        btUpdate = (Button) findViewById(R.id.bt_updateDinTable);

        dinTableDAO = new DinTableDAO(this);

        btUpdate.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String name = etTableName.getText().toString();
        if(name.trim().equals("") || name.trim() != null){
            boolean rs = dinTableDAO.update(idTable,name);
            Intent intent = new Intent();
            intent.putExtra("Result_updateTable",rs);
            setResult(Activity.RESULT_OK,intent);
            finish();
        } else{
            Toast.makeText(this,"Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}