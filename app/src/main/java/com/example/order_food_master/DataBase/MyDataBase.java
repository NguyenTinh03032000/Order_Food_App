package com.example.order_food_master.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MyDataBase extends SQLiteOpenHelper {

    // Table name
    public static String TB_EMPLOYEES = "Employees";
    public static String TB_FOOD = "Food";
    public static String TB_FOODTYPE = "FoodType";
    public static String TB_DINTABLE = "DiningTable";
    public static String TB_ORDER = "Orders";
    public static String TB_ORDERDETAILS = "OrderDetails";
    // Employees
    public static String TB_EMPLOYEE_ID = "EmployeeID";
    public static String TB_EMPLOYEE_USERNAME = "Username";
    public static String TB_EMPLOYEE_PASSWORD = "Password";
    public static String TB_EMPLOYEE_GENRE = "Genre";
    public static String TB_EMPLOYEE_BIRTHDAY = "Birthday";
    public static String TB_EMPLOYEE_PHONE = "Phone";
    // Food
    public static String TB_FOOD_ID = "FoodID";
    public static String TB_FOOD_NAME = "Name";
    public static String TB_FOOD_PRICE = "Price";
    public static String TB_FOOD_TYPE = "FoodTypeID";
    public static String TB_FOOD_IMAGE = "Image";
    // Food type
    public static String TB_FOODTYPE_ID = "FoodTypeID";
    public static String TB_FOODTYPE_NAME = "Name";
    // Table
    public static String TB_TABLE_ID = "DiningTableID";
    public static String TB_TABLE_NAME = "Name";
    public static String TB_TABLE_STATUS = "Status";
    // Order
    public static String TB_ORDER_ID = "OrderID";
    public static String TB_ORDER_EMPLOYEEID = "EmployeeID";
    public static String TB_ORDER_TABLEID = "TableID";
    public static String TB_ORDER_ORDERDATE = "OrderDate";
    public static String TB_ORDER_STATUS = "Status";
    // Order details
    public static String TB_ORDERDETAILS_ID = "OrderDetailsID";
    public static String TB_ORDERDETAILS_ORDERID = "OrderID";
    public static String TB_ORDERDETAILS_FOODID = "FoodID";
    public static String TB_ORDERDETAILS_QUANTITY = "Quantity";


    public MyDataBase(Context context) {
        super(context, "dbOrderFood", null, 1);
    }

    public MyDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbEmploy = "CREATE TABLE " + TB_EMPLOYEES + " ( " + TB_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_EMPLOYEE_USERNAME + " TEXT, " + TB_EMPLOYEE_PASSWORD + " TEXT, " + TB_EMPLOYEE_GENRE + " INTEGER, "
                + TB_EMPLOYEE_BIRTHDAY + " TEXT, " + TB_EMPLOYEE_PHONE + " INTEGER )";
        String tbTable = "CREATE TABLE " + TB_DINTABLE + " ( " + TB_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + TB_TABLE_NAME + " TEXT, " + TB_TABLE_STATUS + " TEXT )";
        String tbFood = "CREATE TABLE " + TB_FOOD + " ( " + TB_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_FOOD_NAME + " TEXT, " + TB_FOOD_TYPE + " INTEGER, " + TB_FOOD_PRICE + " TEXT, "
                + TB_FOOD_IMAGE + " TEXT )";
        String tbFoodType = "CREATE TABLE " + TB_FOODTYPE + " ( " + TB_FOODTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_FOODTYPE_NAME + " TEXT )";
        String tbOrder = "CREATE TABLE " + TB_ORDER + " ( " + TB_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_ORDER_TABLEID + " INTEGER, " + TB_ORDER_EMPLOYEEID + " INTEGER, " + TB_ORDER_ORDERDATE + " TEXT, "
                + TB_ORDER_STATUS + " TEXT )";
        String tbOrderDetails = "CREATE TABLE " + TB_ORDERDETAILS + " ( " + TB_ORDERDETAILS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_ORDERDETAILS_ORDERID + " INTEGER, " + TB_ORDERDETAILS_FOODID + " INTEGER, " + TB_ORDERDETAILS_QUANTITY + " INTEGER )";

        sqLiteDatabase.execSQL(tbEmploy);
        sqLiteDatabase.execSQL(tbTable);
        sqLiteDatabase.execSQL(tbFood);
        sqLiteDatabase.execSQL(tbFoodType);
        sqLiteDatabase.execSQL(tbOrder);
        sqLiteDatabase.execSQL(tbOrderDetails);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
