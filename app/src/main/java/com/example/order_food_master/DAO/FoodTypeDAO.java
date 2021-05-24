package com.example.order_food_master.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.order_food_master.DTO.FoodTypeDTO;
import com.example.order_food_master.DataBase.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class FoodTypeDAO {
    SQLiteDatabase database;

    public FoodTypeDAO(Context context){
        MyDataBase myDatabase = new MyDataBase(context);
        database = myDatabase.open();
    }

    public List<FoodTypeDTO> getAllList(){
        List<FoodTypeDTO> list = new ArrayList<FoodTypeDTO>();
        String query = "Select * from " + MyDataBase.TB_FOODTYPE;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            FoodTypeDTO dto = new FoodTypeDTO();
            dto.setId(cursor.getInt(0));
            // other way: dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_TABLE_ID)));
            dto.setName(cursor.getString(1));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean insert(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_FOODTYPE_NAME, name);

        long result = database.insert(MyDataBase.TB_FOODTYPE,null,contentValues);
        if (result != 0)
            return true;
        return false;
    }

    public String getListImageFoodType(int idFoodType){
        String pathImage = "";
        String query = "Select * from " + MyDataBase.TB_FOOD + " WHERE " + MyDataBase.TB_FOOD_TYPE
                + " = '" + idFoodType + "' AND " + MyDataBase.TB_FOOD_IMAGE + " != '' ORDER BY " +  MyDataBase.TB_FOOD_ID
                + " LIMIT 1 ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            pathImage = cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_IMAGE));
            cursor.moveToNext();
        }
        return pathImage;
    }
}
