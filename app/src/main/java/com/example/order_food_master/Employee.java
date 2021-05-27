package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.order_food_master.Adapter.AdapterEmployee;
import com.example.order_food_master.DAO.EmployeeDAO;
import com.example.order_food_master.DTO.EmployeeDTO;
import com.example.order_food_master.DangKy;

import java.util.List;

public class Employee extends AppCompatActivity {
    ListView lvEmployee;
    List<EmployeeDTO> listEmployee;
    EmployeeDAO employeeDAO;
    AdapterEmployee adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        lvEmployee = (ListView)findViewById(R.id.lv_employee);
        ((Employee)this).getSupportActionBar().setTitle("Nhân viên");
        employeeDAO = new EmployeeDAO(this);
        setUpListView();
        registerForContextMenu(lvEmployee);
    }
    private void setUpListView() {
        listEmployee = employeeDAO.getAllList();
        adapter = new AdapterEmployee(this,R.layout.custom_layout_employee,listEmployee);
        lvEmployee.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_AddEmployee:
                Intent intent = new Intent(this, DangKy.class);
                startActivity(intent);
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
        }
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getMenuInflater().inflate(R.menu.eddit_context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int idEmploy = listEmployee.get(position).getId();

        switch (id){
            case R.id.item_change:
                Intent intent = new Intent(this, DangKy.class);
                intent.putExtra("T_idEmploy",idEmploy);
                startActivity(intent);
                break;
            case R.id.item_delete:
                boolean rs = employeeDAO.delete(idEmploy);
                if(rs){
                    setUpListView();
                    Toast.makeText(this,"Xóa thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setUpListView();
    }
}