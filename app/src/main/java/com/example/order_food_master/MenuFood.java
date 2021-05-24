package com.example.order_food_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuFood extends AppCompatActivity {
    public static int REQUEST_CODE_INSERT = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_food);
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
        }
        return super.onOptionsItemSelected(item);
    }
}