package com.example.order_food_master.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.order_food_master.DTO.DinTableDTO;
import com.example.order_food_master.DataBase.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class DinTableDAO {
    SQLiteDatabase database;

    public DinTableDAO(Context context) {
        MyDataBase myDataBase = new MyDataBase(context);
        database = myDataBase.open();
    }
    public List<DinTableDTO> getAllList() {
        List<DinTableDTO> list = new ArrayList<>();
        String query = "Select * from " + MyDataBase.TB_DINTABLE;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DinTableDTO dto = new DinTableDTO();
            dto.setId(cursor.getInt(0));
            dto.setName(cursor.getString(1));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }
    public boolean setStatusTableById(int id, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_TABLE_STATUS, status);
        long rs = database.update(MyDataBase.TB_DINTABLE, contentValues, MyDataBase.TB_TABLE_ID
                + "='" + id + "'", null);
        if (rs != 0) {
            return true;
        } else {
            return false;
        }
    }
    public String getStatusTableById(int id) {
        String status = "";
        String query = "Select * from " + MyDataBase.TB_DINTABLE + " WHERE " +
                MyDataBase.TB_TABLE_ID + " = '" + id + "'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            status = cursor.getString(cursor.getColumnIndex(MyDataBase.TB_TABLE_STATUS));
            cursor.moveToNext();
        }
        return status;
    }
    public boolean insert(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_TABLE_NAME, name);
        contentValues.put(MyDataBase.TB_TABLE_STATUS, "false");

        long result = database.insert(MyDataBase.TB_DINTABLE, null, contentValues);
        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean update(int idTable, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_TABLE_NAME, name);
        long rs = database.update(MyDataBase.TB_DINTABLE, contentValues, MyDataBase.TB_TABLE_ID + " = '" + idTable + "'", null);
        if (rs != 0)
            return true;
        return false;
    }
    public boolean delete(int idTable) {
        long rs = database.delete(MyDataBase.TB_DINTABLE, MyDataBase.TB_TABLE_ID + " = " + idTable, null);
        if (rs != 0)
            return true;
        return false;
    }
}


