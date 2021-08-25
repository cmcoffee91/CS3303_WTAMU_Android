package com.phonebook.cc916647.phonebook;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        Context context = ShowAllActivity.this;

        TableLayout rootLayout = findViewById(R.id.activityShowAllRoot);

        Cursor allContactsCursor = getContentResolver().query(MainActivity.CONTENT_URI,
                null,
                null,
                null,
                null);

        if(allContactsCursor != null) {

            MyContact[] allContacts = findAll(allContactsCursor);

            TableLayout.LayoutParams rowHolderParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
            //layout params must use it's parent's "type" params
            TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            textViewParams.weight = 1;

            for (int i = 0; i < allContacts.length; i++) {

                TableRow rowHolder = new TableRow(context);
                rowHolder.setOrientation(TableRow.HORIZONTAL);
                rowHolder.setLayoutParams(rowHolderParams);

                TextView firstNameView = new TextView(context);
                firstNameView.setLayoutParams(textViewParams);
                firstNameView.setText(allContacts[i].getF_name());

                TextView lastNameView = new TextView(context);
                lastNameView.setLayoutParams(textViewParams);
                lastNameView.setText(allContacts[i].getL_name());

                TextView phoneNumberView = new TextView(context);
                phoneNumberView.setLayoutParams(textViewParams);
                phoneNumberView.setText(allContacts[i].getPhone_number());

                rowHolder.addView(firstNameView);
                rowHolder.addView(lastNameView);
                rowHolder.addView(phoneNumberView);

                rootLayout.addView(rowHolder);
            }
        }




    }

    private MyContact[] findAll(Cursor cursor){

        int n = cursor.getCount();

        //  Log.i("find All" , "number of records  " + n);
        MyContact [] ar = new MyContact[n];
        for(int i = 0; i < n; i++){
            MyContact p = new MyContact();
            cursor.moveToNext();
            //  p.setId(Integer.parseInt(cursor.getString(0)));
            p.setF_name(cursor.getString(1));
            p.setL_name(cursor.getString(2));
            p.setPhone_number(cursor.getString(3));
            Log.i("find All" , "records  " + cursor.getString(1) +
                    ", " + cursor.getString(2) + ", " +
                    cursor.getString(3));
            ar[i] = p;

        }

        return ar;
    }


}
