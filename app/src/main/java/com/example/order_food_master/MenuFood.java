package com.example.order_food_master;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.order_food_master.DAO.DinTableDAO;
import com.example.order_food_master.DTO.FoodTypeDTO;
import com.example.order_food_master.DAO.FoodTypeDAO;

import java.util.List;
import com.example.order_food_master.Adapter.AdapterFoodTypeWithImage;


public class MenuFood extends AppCompatActivity {
    public static int REQUEST_CODE_INSERT = 102;
    GridView gvMenuFood;
    List<FoodTypeDTO> listFoodType;
    FoodTypeDAO foodTypeDAO;
    int idTable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_food);
        foodTypeDAO = new FoodTypeDAO(this);
        listFoodType = foodTypeDAO.getAllList();
        gvMenuFood = (GridView) findViewById(R.id.gv_menuFood);

        AdapterFoodTypeWithImage adapter = new AdapterFoodTypeWithImage(this,R.layout.custom_layout_typefood,listFoodType);
        gvMenuFood.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Intent intent=getIntent();
        Bundle bundleDataMenu=intent.getBundleExtra("To_DinTable");
        if(bundleDataMenu != null){
            idTable = bundleDataMenu.getInt("B_data_idTable");
        }

        gvMenuFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int idType = listFoodType.get(position).getId();
                Intent intent=new Intent(MenuFood.this,ListFood.class);
                Bundle bundle = new Bundle();
                bundle.putInt("B_idFoodType",idType);
                bundle.putInt("B_data_idTable",idTable);
                intent.putExtra("To_MenuFood",bundle);
                Toast.makeText(MenuFood.this,""+idTable,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menugroupfood,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_AddFood:
                Intent intent = new Intent(this, InsertMenuFood.class);
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
        }
        return super.onOptionsItemSelected(item);
    }
}