package com.example.order_food_master.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.order_food_master.DTO.OrderDetailDTO;
import com.example.order_food_master.DTO.OrdersDTO;
import com.example.order_food_master.DTO.PaymentDTO;
import com.example.order_food_master.DataBase.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    SQLiteDatabase database;


    public OrdersDAO(Context context){
        MyDataBase myDatabase = new MyDataBase(context);
        database = myDatabase.open();
    }

    public long insert(OrdersDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_ORDER_EMPLOYEEID, dto.getEmployID());
        contentValues.put(MyDataBase.TB_ORDER_TABLEID,dto.getTableID());
        contentValues.put(MyDataBase.TB_ORDER_ORDERDATE,dto.getDateOrder());
        contentValues.put(MyDataBase.TB_ORDER_STATUS,dto.getStatus());

        long idOrder = database.insert(MyDataBase.TB_ORDER,null,contentValues);
        return idOrder;
    }

    public long getIdOrderByIdTable(int idTable,String status){
        String query = "Select * from " + MyDataBase.TB_ORDER + " where " + MyDataBase.TB_ORDER_TABLEID
                + " = '" + idTable + "' and " + MyDataBase.TB_ORDER_STATUS + " = '" + status + "'";
        long idOrder = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            idOrder = cursor.getLong(cursor.getColumnIndex(MyDataBase.TB_ORDER_ID));
            cursor.moveToNext();
        }
        return idOrder;
    }

    public boolean updateStatusOrderWithTable(int idTable,String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_ORDER_STATUS,status);

        long rs = database.update(MyDataBase.TB_ORDER,contentValues, MyDataBase.TB_ORDER_TABLEID + " = '" + idTable
                + "'",null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean checkFoodHasExist(int idOrder, int idFood){
        String query = "Select * from " + MyDataBase.TB_ORDERDETAILS + " where " + MyDataBase.TB_ORDERDETAILS_FOODID
                + " = " + idFood + " and " + MyDataBase.TB_ORDERDETAILS_ORDERID + " = " + idOrder;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0)
            return true;
        return false;
    }

    public int getQuantityFoodByOrder(int idOrder, int idFood){
        int quantity = 0;
        String query = "Select * from " + MyDataBase.TB_ORDERDETAILS + " where " +MyDataBase.TB_ORDERDETAILS_ORDERID
                + " = " + idOrder + " and " + MyDataBase.TB_ORDERDETAILS_FOODID + " = " + idFood;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            quantity = cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_ORDERDETAILS_QUANTITY));
            cursor.moveToNext();
        }
        return quantity;
    }

    public boolean updateQuantity(OrderDetailDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_ORDERDETAILS_QUANTITY, dto.getQuantity());
        long rs = database.update(MyDataBase.TB_ORDERDETAILS,contentValues ,MyDataBase.TB_ORDERDETAILS_ORDERID + " = " + dto.getIdOrder()
                + " and " + MyDataBase.TB_ORDERDETAILS_FOODID + " = " + dto.getIdFood(),null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean insertDetail(OrderDetailDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.TB_ORDERDETAILS_ORDERID,dto.getIdOrder());
        contentValues.put(MyDataBase.TB_ORDERDETAILS_FOODID,dto.getIdFood());
        contentValues.put(MyDataBase.TB_ORDERDETAILS_QUANTITY,dto.getQuantity());

        long rs = database.insert(MyDataBase.TB_ORDERDETAILS,null,contentValues);
        if (rs != 0)
            return true;
        return false;
    }

    public List<PaymentDTO> getListFoodOrderWithTable(int idOrder){
        String query = "Select * from " + MyDataBase.TB_ORDERDETAILS + " od, " + MyDataBase.TB_FOOD
                + " f where od." + MyDataBase.TB_ORDERDETAILS_FOODID + " = f." + MyDataBase.TB_FOOD_ID
                + " and od." + MyDataBase.TB_ORDERDETAILS_ORDERID + " = " + idOrder;
        List<PaymentDTO> list = new ArrayList<PaymentDTO>();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PaymentDTO dto = new PaymentDTO();
            dto.setNameFood(cursor.getString(cursor.getColumnIndex(MyDataBase.TB_FOOD_NAME)));
            dto.setQuantity(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_ORDERDETAILS_QUANTITY)));
            dto.setPrice(cursor.getInt(cursor.getColumnIndex(MyDataBase.TB_FOOD_PRICE)));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

}
