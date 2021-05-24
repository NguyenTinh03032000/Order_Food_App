package com.example.order_food_master.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.order_food_master.DTO.FoodDTO;
import com.example.order_food_master.DataBase.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    SQLiteDatabase database;

    public FoodDAO(Context context){
        MyDataBase myDatabase = new MyDataBase(context);
        database = myDatabase.open();
    }

    public FoodDTO getFoodWithID(int idFood){
        FoodDTO dto = new FoodDTO();
        String query = "Select * from " + MyDataBase.TB_FOOD + " WHERE " + MyDataBase.TB_FOOD_ID
                + " = '" + idFood + "'";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_FOOD_ID)));
            dto.setName(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_NAME)));
            dto.setPrice(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_PRICE)));
            dto.setImage(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_IMAGE)));
            dto.setId_foodType(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_FOOD_TYPE)));
            return dto;
        } else {
            return null;
        }
    }

    public List<FoodDTO> getAllList(){
        List<FoodDTO> list = new ArrayList<FoodDTO>();
        String query = "Select * from " + MyDataBase.TB_FOOD;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            FoodDTO dto = new FoodDTO();
            dto.setId(cursor.getInt(0));
            // other way: dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_TABLE_ID)));
            dto.setName(cursor.getString(1));
            dto.setPrice(cursor.getString(2));
            dto.setImage(cursor.getString(3));
            dto.setId_foodType(cursor.getInt(4));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean insert(FoodDTO dto){
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyDataBase.TB_FOOD_NAME, dto.getName());
        contentValues.put(MyDataBase.TB_FOOD_PRICE, dto.getPrice());
        contentValues.put(MyDataBase.TB_FOOD_TYPE, dto.getId_foodType());
        contentValues.put(MyDataBase.TB_FOOD_IMAGE, dto.getImage());

        long result = database.insert(MyDataBase.TB_FOOD,null,contentValues);
        if (result != 0)
            return true;
        return false;
    }

    public boolean update(FoodDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_FOOD_ID, dto.getId());
        contentValues.put(MyDataBase.TB_FOOD_NAME,dto.getName());
        contentValues.put(MyDataBase.TB_FOOD_PRICE, dto.getPrice());
        contentValues.put(MyDataBase.TB_FOOD_TYPE, dto.getId_foodType());
        contentValues.put(MyDataBase.TB_FOOD_IMAGE, dto.getImage());

        long rs = database.update(MyDataBase.TB_FOOD,contentValues,MyDataBase.TB_FOOD_ID + " = " + dto.getId(),null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean delete(int idFood){
        long rs = database.delete(MyDataBase.TB_FOOD, MyDataBase.TB_FOOD_ID + " = " + idFood,null);
        if(rs != 0 )
            return true;
        return false;
    }

    public List<FoodDTO> getAllListWithFoodType(int idFoodType){
        List<FoodDTO> list = new ArrayList<FoodDTO>();
        String query = "Select * from " + MyDataBase.TB_FOOD + " WHERE " + MyDataBase.TB_FOOD_TYPE + " = '" + idFoodType + "'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            FoodDTO dto = new FoodDTO();
            dto.setId(cursor.getInt(0));
            // other way: dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_TABLE_ID)));
            dto.setName(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_NAME)));
            dto.setPrice(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_PRICE)));
            dto.setId_foodType(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_FOOD_TYPE)));
            dto.setImage(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_IMAGE))+"");
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }
}
