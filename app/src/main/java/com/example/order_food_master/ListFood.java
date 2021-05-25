package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;
import com.example.order_food_master.DAO.FoodDAO;
import com.example.order_food_master.DTO.FoodDTO;
import com.example.order_food_master.Adapter.AdapterListFood;

public class ListFood extends AppCompatActivity {
    GridView gvListFood;
    FoodDAO foodDAO;
    List<FoodDTO> listFood;
    AdapterListFood adapter;
    int idTable;
    int idType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        gvListFood = (GridView)findViewById(R.id.gv_menuFood);
        foodDAO = new FoodDAO(this);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("To_MenuFood");
        if(bundle != null){
            idType = bundle.getInt("B_idFoodType");
            idTable = bundle.getInt("B_data_idTable");
            showListFood(idType);
            registerForContextMenu(gvListFood);
            gvListFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(idTable != 0){
                        Intent intent = new Intent(ListFood.this, FoodQuantityActivity.class);
                        intent.putExtra("T_idTable",idTable);
                        intent.putExtra("T_idFood",listFood.get(i).getId());
                        startActivity(intent);
                    }
                }
            });
        }
    }
    private void showListFood(int idType) {
        listFood = foodDAO.getAllListWithFoodType(idType);
        adapter = new AdapterListFood(ListFood.this, R.layout.custom_layout_listfood,listFood);
        gvListFood.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        int idFood = listFood.get(position).getId();

        switch (id){
            case R.id.item_change:
                Intent intent = new Intent(ListFood.this, InsertMenuFood.class);
                intent.putExtra("T_idFood",idFood);
                startActivity(intent);
                break;
            case R.id.item_delete:
                boolean rs = foodDAO.delete(idFood);
                if(rs){
                    showListFood(idType);
                    Toast.makeText(ListFood.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(ListFood.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}