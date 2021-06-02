package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food_master.DTO.FoodTypeDTO;
import com.example.order_food_master.DAO.FoodTypeDAO;
import com.example.order_food_master.DAO.FoodDAO;
import com.example.order_food_master.Adapter.AdapterFoodType;
import com.example.order_food_master.DTO.FoodDTO;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InsertMenuFood extends AppCompatActivity implements View.OnClickListener{
    EditText etFoodName, etFoodPrice;
    Spinner spinFoodType;
    ImageButton btAddType;
    ImageView imgFood;
    Button btInsert,btExit;
    TextView tvTitle;
    public static int REQUESTCODE_INSERT_FOODTYPE = 301;
    public static int REQUESTCODE_INSERT_IMAGEFOOD = 201;
    List<FoodTypeDTO> listFoodType;
    FoodTypeDAO foodTypeDAO;
    FoodDAO foodDAO;
    AdapterFoodType adapter;
    String urlImageFood;
    int idFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_menu_food);
        addControls();
        idFood = getIntent().getIntExtra("T_idFood",0);

        if(idFood != 0){
            // update food
            FoodDTO foodDTO = foodDAO.getFoodWithID(idFood);
            tvTitle.setText("Cập nhật thông tin");
            etFoodName.setText(foodDTO.getName());
            etFoodPrice.setText(foodDTO.getPrice());
            Uri uri = Uri.parse(foodDTO.getImage());
            imgFood.setImageURI(uri);//đã fix
            urlImageFood = foodDTO.getImage();
            int idType = foodDTO.getId_foodType();
            for(int i = 0; i<listFoodType.size();i++){
                if(listFoodType.get(i).getId() == idType){
                    spinFoodType.setSelection(i);
                }
            }
        }
    }
    private void addControls() {
        etFoodName = (EditText) findViewById(R.id.et_insert_foodName);
        etFoodPrice = (EditText) findViewById(R.id.et_insert_foodPrice);
        spinFoodType = (Spinner) findViewById(R.id.spin_menuTypeFood);
        imgFood = (ImageView) findViewById(R.id.imgView_food);
        btAddType = (ImageButton) findViewById(R.id.bt_addTypeFood);
        btInsert = (Button) findViewById(R.id.bt_insertFood);
        btExit = (Button) findViewById(R.id.bt_exitFood);
        tvTitle = (TextView) findViewById(R.id.tv_food_title);

        btAddType.setOnClickListener(this);
        btInsert.setOnClickListener(this);
        btExit.setOnClickListener(this);
        imgFood.setOnClickListener(this);

        foodTypeDAO = new FoodTypeDAO(this);
        foodDAO = new FoodDAO(this);
        showListFoodtype();
    }
    private void showListFoodtype(){
        listFoodType = foodTypeDAO.getAllList();
        adapter = new AdapterFoodType(InsertMenuFood.this,R.layout.custom_layout_spintfoodtype,listFoodType);
        spinFoodType.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_addTypeFood:
                Intent intent = new Intent(InsertMenuFood.this,InsertFoodType.class);
                startActivityForResult(intent,REQUESTCODE_INSERT_FOODTYPE);
                break;
            case R.id.bt_insertFood:
                if(idFood != 0){
                    // update
                    processUpdate();
                } else{
                    // insert
                    processInsert();
                }
                break;
            case R.id.bt_exitFood:
                finish();
                break;
            case R.id.imgView_food:
                if(Build.VERSION.SDK_INT<19){
                    Intent intentImg = new Intent();
                    intentImg.setType("image/*");
                    intentImg.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intentImg,"Chọn hình ảnh"),REQUESTCODE_INSERT_IMAGEFOOD);
                }else{
                    Intent intentImg = new Intent();
                    intentImg.setType("image/*");
                    intentImg.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    startActivityForResult(Intent.createChooser(intentImg,"Chọn hình ảnh"),REQUESTCODE_INSERT_IMAGEFOOD);
                }
                break;
        }
    }
    public void processUpdate(){
        int position = spinFoodType.getSelectedItemPosition();
        int idType = listFoodType.get(position).getId();
        String name = etFoodName.getText().toString();
        String price = etFoodPrice.getText().toString();

        if (name != null && price != null && !name.equals("") && !price.equals("")) {
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setId(idFood);
            foodDTO.setName(name);
            foodDTO.setPrice(price);
            foodDTO.setId_foodType(idType);
            foodDTO.setImage(urlImageFood);
            boolean rs = foodDAO.update(foodDTO);
            if(rs){
                Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this,"Cập nhật không thành công", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }
    private void processInsert() {
        int position = spinFoodType.getSelectedItemPosition();
        int idType = listFoodType.get(position).getId();
        String name = etFoodName.getText().toString();
        String price = etFoodPrice.getText().toString();

        if (name != null && price != null && !name.equals("") && !price.equals("")) {
            FoodDTO foodDTO = new FoodDTO();

            foodDTO.setName(name);
            foodDTO.setPrice(price);
            foodDTO.setId_foodType(idType);
            foodDTO.setImage(urlImageFood);
            boolean rs = foodDAO.insert(foodDTO);
            if(rs){
                Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_INSERT_FOODTYPE){
            if(resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean rs = intent.getBooleanExtra("Result_insertFoodType",false);
                if (rs){
                    showListFoodtype();
                    Toast.makeText(this,"Thêm thành công", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this,"Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        } else if(requestCode == REQUESTCODE_INSERT_IMAGEFOOD){
            if(resultCode == Activity.RESULT_OK && data != null){
                urlImageFood = data.getData().toString();
                imgFood.setImageURI(data.getData());
                Log.d("Path image là:",data.getData() + "");
            }
        }
    }
}