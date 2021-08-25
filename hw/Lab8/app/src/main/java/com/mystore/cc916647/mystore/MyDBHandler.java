package com.mystore.cc916647.mystore;

/**
 * Created by cmcoffee91 on 4/9/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCT = "product";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCT_NAME = "productName";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";
    private static final int DATABASE_VERSION = 1;

    public MyDBHandler(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_product_table = "CREATE TABLE " + TABLE_PRODUCT +"(" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT_NAME + " TEXT," +
                COLUMN_QUANTITY + " INTEGER," + COLUMN_PRICE + " TEXT )";
        db.execSQL(create_product_table);

        /*
         db.execSQL("create table " + CarTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CarTable.Cols.ID + ", " +
                CarTable.Cols.YEAR + ", " +
                CarTable.Cols.MAKE + ", " +
                CarTable.Cols.MODEL + ", " +
                CarTable.Cols.PRICE + ", " +
                CarTable.Cols.RENTED + ", " +
                CarTable.Cols.VIN + ", " +
                CarTable.Cols.DATE + ", " +
                CarTable.Cols.MILEAGE + ", " +
                CarTable.Cols.NOTES + ", " +
                CarTable.Cols.PIC_LOC +
                ")"
        );
         */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("MyDBHandler", "Updating db from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    // helper method to work with the database when a button is clicked
    public void addProduct(StoreItem product){
        // need an instance of ContentValue to store the values of a record
        ContentValues value = new ContentValues();
        value.put(COLUMN_PRODUCT_NAME, product.getName());
        value.put(COLUMN_QUANTITY, product.getQuantity());
        value.put(COLUMN_PRICE, String.valueOf(product.getPrice()));
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_PRODUCT, null, value);
        database.close();
    }

    public StoreItem[] findAll(){
        String query = "select * from " + TABLE_PRODUCT;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        int n = cursor.getCount();

        //  Log.i("find All" , "number of records  " + n);
        StoreItem [] ar = new StoreItem[n];
        for(int i = 0; i < n; i++){
            StoreItem p = new StoreItem();
            cursor.moveToNext();
          //  p.setId(Integer.parseInt(cursor.getString(0)));
            p.setName(cursor.getString(1));
            p.setQuantity(cursor.getInt(2));
            p.setPrice(Double.parseDouble(cursor.getString(3)));
              Log.i("find All" , "records  " + cursor.getString(1) +
                 ", " + cursor.getString(2) + ", " +
                     cursor.getString(3));
            ar[i] = p;

        }
        database.close();
        return ar;
    }


    public StoreItem findProduct( String productName){
        String query =  "select * from " + TABLE_PRODUCT + " where "+
                COLUMN_PRODUCT_NAME + " = \"" + productName + "\"";

        StoreItem p = new StoreItem();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_PRODUCT,null,COLUMN_PRODUCT_NAME + "= ?",new String []{productName},null,null,null);
        // Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
         //   p.setId(cursor.getInt(0));
            p.setName(cursor.getString(1));
            p.setQuantity(cursor.getInt(2));
            p.setPrice(cursor.getDouble(3));
        }
        else
            p = null;
        cursor.close();
        database.close();
        return  p;
    }

    public int deleteProduct(String productName){
        String query = "select * from " + TABLE_PRODUCT + " where " + COLUMN_PRODUCT_NAME + " = \"" + productName +"\"";
        // Log.i("findProduct", query);
        int n = 0;
        SQLiteDatabase database = getWritableDatabase();
        n = database.delete(TABLE_PRODUCT, COLUMN_PRODUCT_NAME + "=?",new String []{productName});
        //   Log.i("findProduct", productName + n + " deleted");
        database.close();
        return  n;

    }
}


