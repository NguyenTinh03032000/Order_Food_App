package com.example.order_food_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.order_food_master.DAO.DinTableDAO;
import com.example.order_food_master.DTO.DinTableDTO;
import com.example.order_food_master.Adapter.AdapterDinTables;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_CODE_INSERT = 101;
    public static int REQUEST_CODE_UPDATE = 105;
    List<DinTableDTO> listDinTable;
    DinTableDAO dinTableDAO;
    AdapterDinTables adapter;
    GridView gvDinTables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (MainActivity.this).getSupportActionBar().setTitle("Trang chủ");
        gvDinTables = (GridView) findViewById(R.id.GvTable);
        dinTableDAO = new DinTableDAO(this);//các thao tác với table
        showListDinTable();
        registerForContextMenu(gvDinTables);
    }
    private void showListDinTable() {
        listDinTable = dinTableDAO.getAllList();//lấy ds table
        adapter = new AdapterDinTables(MainActivity.this, R.layout.custom_layout_dintables,listDinTable);//có show button
        gvDinTables.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MainActivity.this.getMenuInflater().inflate(R.menu.eddit_context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int idTable = listDinTable.get(position).getId();

        switch (id){
            case R.id.item_change:
                Intent intent = new Intent(MainActivity.this, UpdateDinTable.class);
                intent.putExtra("T_idTable",idTable);
                startActivityForResult(intent,REQUEST_CODE_UPDATE);
                break;
            case R.id.item_delete:
                boolean rs = dinTableDAO.delete(idTable);
                if(rs){
                    showListDinTable();
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this,"Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_AdddinTable:
                Intent intent = new Intent(this, InsertDinTable.class);
                startActivityForResult(intent,REQUEST_CODE_INSERT);
                break;
            case R.id.menu_main:
                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.menu_menufood:
                Intent intentFood = new Intent(this, MenuFood.class);
                startActivity(intentFood);
                break;
            case R.id.menu_employee:
                Intent intentEmployee = new Intent(this, Employee.class);
                startActivity(intentEmployee);
                break;
            case R.id.menu_map:
                Intent intentMap = new Intent(this, Map.class);
                startActivity(intentMap);
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
                    showListDinTable();
                    Toast.makeText(this,"Thêm thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this,"Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean rs = intent.getBooleanExtra("Result_updateTable",false);
                showListDinTable();
                if(rs){
                    Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this,"Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}