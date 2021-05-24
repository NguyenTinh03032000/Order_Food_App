package com.example.order_food_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.order_food_master.DAO.DinTableDAO;
import com.example.order_food_master.DTO.DinTableDTO;
import com.example.order_food_master.Adapter.AdapterDinTables;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_CODE_INSERT = 101;
    List<DinTableDTO> listDinTable;
    DinTableDAO dinTableDAO;
    AdapterDinTables adapter;
    GridView gvDinTables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gvDinTables = (GridView) findViewById(R.id.GvTable);
        dinTableDAO = new DinTableDAO(this);
        showListDinTable();
    }
    private void showListDinTable() {
        listDinTable = dinTableDAO.getAllList();
        adapter = new AdapterDinTables(this, R.layout.custom_layout_dintables,listDinTable);
        gvDinTables.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_AdddinTable:
                Intent intent = new Intent(this, InsertDinTable.class);
                startActivityForResult(intent,REQUEST_CODE_INSERT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_INSERT){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean rs = intent.getBooleanExtra("Result_insertTable",false);
                if(rs){
                    //showListDinTable();
                    Toast.makeText(this,"Thêm thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this,"Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}