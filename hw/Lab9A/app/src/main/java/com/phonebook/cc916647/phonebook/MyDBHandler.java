package com.phonebook.cc916647.phonebook;

/**
 * Created by cmcoffee91 on 4/14/18.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;


public class MyDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myContacts.db";
    private static final String TABLE_CONTACTS = "my_contacts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_PHONE = "phone";
    private static final int DATABASE_VERSION = 1;

    public MyDBHandler(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_product_table = "CREATE TABLE " + TABLE_CONTACTS +"(" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRST_NAME + " TEXT," +
                COLUMN_LAST_NAME + " TEXT," + COLUMN_PHONE + " TEXT )";
        db.execSQL(create_product_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("MyDBHandler", "Updating db from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // helper method to work with the database when a button is clicked
    public void addContact(MyContact contact){
        // need an instance of ContentValue to store the values of a record
        ContentValues value = new ContentValues();
        value.put(COLUMN_FIRST_NAME, contact.getF_name());
        value.put(COLUMN_LAST_NAME, contact.getL_name());
        value.put(COLUMN_PHONE, contact.getPhone_number());
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_CONTACTS, null, value);
        database.close();
    }

    public Uri addContactProvider(Uri uri, ContentValues values)
    {

        SQLiteDatabase database = getWritableDatabase();

        long success = database.insert(TABLE_CONTACTS, null, values);

        if(success == -1)
        {
            return null;
        }
        else
        {
            return ContentUris.withAppendedId(uri, success);
        }
    }

    public Cursor findOneProvider(String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {

        //hard code selection for sanity check

        SQLiteDatabase database = getReadableDatabase();
        return database.query(
                TABLE_CONTACTS,
                projection, // Columns - null selects all columns
                COLUMN_FIRST_NAME + "= ? AND " + COLUMN_LAST_NAME + " = ? ", // where clause
                new String []{selectionArgs[0],selectionArgs[1]}, // where args, get first and last name
                null, // groupBy
                null, // having
                sortOrder  // orderBy
        );
    }

    public Cursor findAllProvider(String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {

        SQLiteDatabase database = getReadableDatabase();
        return database.query(
                TABLE_CONTACTS,
                null, // Columns - null selects all columns
                selection, // where clause
                selectionArgs, // where args
                null, // groupBy
                null, // having
                sortOrder  // orderBy
        );
    }





    public MyContact findContact( String firstName, String lastName){
        String query =  "select * from " + TABLE_CONTACTS + " where "+
                COLUMN_FIRST_NAME + " = \"" + firstName + "\"";

        MyContact p = new MyContact();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_CONTACTS,null,COLUMN_FIRST_NAME + "= ? AND " + COLUMN_LAST_NAME + " = ? ",new String []{firstName,lastName},null,null,null);
        // Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            //   p.setId(cursor.getInt(0));
            p.setF_name(cursor.getString(1));
            p.setL_name(cursor.getString(2));
            p.setPhone_number(cursor.getString(3));
        }
        else
            p = null;
        cursor.close();
        database.close();
        return  p;
    }


    public int deleteContactProvider(String firstName, String lastName){
        String query = "select * from " + TABLE_CONTACTS + " where " + COLUMN_FIRST_NAME + " = \"" + firstName +"\"";
        // Log.i("findProduct", query);
        int n = 0;
        SQLiteDatabase database = getWritableDatabase();
        n = database.delete(TABLE_CONTACTS, COLUMN_FIRST_NAME + " = ? AND " + COLUMN_LAST_NAME + " = ? ",new String []{firstName,lastName});
        //   Log.i("findProduct", productName + n + " deleted");
        database.close();
        return  n;

    }
}


