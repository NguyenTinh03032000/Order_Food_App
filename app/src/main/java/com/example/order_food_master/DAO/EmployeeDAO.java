package com.example.order_food_master.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.order_food_master.DTO.EmployeeDTO;
import com.example.order_food_master.DataBase.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
        SQLiteDatabase database;

        public EmployeeDAO(Context context) {
            MyDataBase myDataBase = new MyDataBase(context);
            database = myDataBase.open();
        }


    public EmployeeDTO getEmployeeWithID(int idEmploy){
        EmployeeDTO dto = new EmployeeDTO();
        String query = "Select * from " + MyDataBase.TB_EMPLOYEES + " where " + MyDataBase.TB_EMPLOYEE_ID
                + " = '" + idEmploy + "'";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_ID)));
            dto.setUsername(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_USERNAME)));
            dto.setPassword(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_PASSWORD)));
            dto.setGenre(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_GENRE)));
            dto.setBirthday(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_BIRTHDAY)));
            dto.setPhone(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_PHONE)));
            return dto;
        } else {
            return null;
        }
    }

    public int checkEmployees(String username, String password){
        String query = "Select * from " + MyDataBase.TB_EMPLOYEES + " where " + MyDataBase.TB_EMPLOYEE_USERNAME
                + " = '" + username + "' and " + MyDataBase.TB_EMPLOYEE_PASSWORD + " = '" + password + "'";
        int idEmploy = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            idEmploy = cursor.getInt(0);
            cursor.moveToNext();
        }
        return idEmploy;
    }

    public List<EmployeeDTO> getAllList(){
        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
        String query = "Select * from " + MyDataBase.TB_EMPLOYEES;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_ID)));
            dto.setUsername(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_USERNAME)));
            dto.setPassword(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_PASSWORD)));
            dto.setGenre(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_GENRE)));
            dto.setBirthday(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_BIRTHDAY)));
            dto.setPhone(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_EMPLOYEE_PHONE)));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public long insert(EmployeeDTO employeeDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_EMPLOYEE_USERNAME, employeeDTO.getUsername());
        contentValues.put(MyDataBase.TB_EMPLOYEE_PASSWORD, employeeDTO.getPassword());
        contentValues.put(MyDataBase.TB_EMPLOYEE_GENRE, employeeDTO.getGenre());
        contentValues.put(MyDataBase.TB_EMPLOYEE_BIRTHDAY, employeeDTO.getBirthday());
        contentValues.put(MyDataBase.TB_EMPLOYEE_PHONE, employeeDTO.getPhone());

        long result = database.insert(MyDataBase.TB_EMPLOYEES,null,contentValues);
        return result;
    }

    public boolean update(EmployeeDTO employeeDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_EMPLOYEE_USERNAME,employeeDTO.getUsername());
        contentValues.put(MyDataBase.TB_EMPLOYEE_PASSWORD,employeeDTO.getPassword());
        contentValues.put(MyDataBase.TB_EMPLOYEE_GENRE,employeeDTO.getGenre());
        contentValues.put(MyDataBase.TB_EMPLOYEE_BIRTHDAY,employeeDTO.getBirthday());
        contentValues.put(MyDataBase.TB_EMPLOYEE_PHONE,employeeDTO.getPhone());

        long rs = database.update(MyDataBase.TB_EMPLOYEES,contentValues,MyDataBase.TB_EMPLOYEE_ID + " = " + employeeDTO.getId(),null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean delete(int idEmploy){
        long rs = database.delete(MyDataBase.TB_EMPLOYEES,MyDataBase.TB_EMPLOYEE_ID + " = " + idEmploy,null);
        if (rs != 0)
            return true;
        return false;
    }


}
