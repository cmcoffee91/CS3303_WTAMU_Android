package com.makeacall.cc916647.makeacall;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
Members in group :
Chris Coffee
Bryan Polanco

We both contributed an equal amount of work, 50/50,
on this app.

Bryan Polanco:
find method functionality

Chris Coffee
call method functionality
 */

public class MainActivity extends AppCompatActivity {

    private static final String CONTENT_AUTHORITY = "com.phonebook.cc916647.phonebook.MyContentProvider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_CONTACTS = "contacts";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CONTACTS );

    private String phoneNumber = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        final EditText lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);

        Button findButton = (Button) findViewById(R.id.findButton);
        Button callButton = (Button) findViewById(R.id.makeCallButton);

        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setText("");


        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();

                if(!firstName.isEmpty() && !lastName.isEmpty())
                {
                   CallContact currentContact = findContact(firstName,lastName);

                   if(currentContact != null)
                   {
                       String formattedPhone = getFormattedPhone(currentContact.getPhoneNumber());
                       resultTextView.setText(formattedPhone);
                   }
                   else
                   {
                       showToast("Sorry contact was not found");
                       resultTextView.setText("Sorry contact was not found");
                   }
                }
                else
                {
                    showToast("Please enter first and last name!");
                }
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();

                if(!firstName.isEmpty() && !lastName.isEmpty())
                {
                    CallContact currentContact = findContact(firstName,lastName);

                    if(currentContact != null)
                    {
                        makePhoneCall(currentContact.getPhoneNumber());
                    }
                    else
                    {
                        showToast("Sorry contact was not found");
                        resultTextView.setText("Sorry contact was not found");
                    }
                }
                else
                {
                    showToast("Please enter first and last name!");
                }
            }
        });


    }


    private String getFormattedPhone(String phoneNum) {
        StringBuilder sb = new StringBuilder(15);
        StringBuilder temp = new StringBuilder(phoneNum);

        while (temp.length() < 10)
            temp.insert(0, "0");

        char[] chars = temp.toString().toCharArray();

        sb.append("(");
        for (int i = 0; i < chars.length; i++) {
            if (i == 3)
                sb.append(") ");
            else if (i == 6)
                sb.append("-");
            sb.append(chars[i]);
        }

        return sb.toString();
    }

    private void showToast(String toastText)
    {
        Toast.makeText(MainActivity.this, toastText,Toast.LENGTH_LONG).show();
    }




    private CallContact findContact(String firstName, String lastName)
    {
        String selection = "firstName = ? AND lastName = ? ";
        String[] selectionArgs = new String []{firstName,lastName};

        Cursor allContactsCursor = getContentResolver().query(MainActivity.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);

        CallContact[] allContacts = null;

        if(allContactsCursor != null) {
            allContacts = findAll(allContactsCursor);

            if(allContacts.length > 0) {
                return allContacts[0];
            }
        }

        return null;


    }



    public CallContact[] findAll(Cursor cursor){

        int n = cursor.getCount();

        //  Log.i("find All" , "number of records  " + n);
        CallContact [] ar = new CallContact[n];
        for(int i = 0; i < n; i++){
            CallContact p = new CallContact();
            cursor.moveToNext();
            //  p.setId(Integer.parseInt(cursor.getString(0)));
            p.setFirstName(cursor.getString(1));
            p.setLastName(cursor.getString(2));
            p.setPhoneNumber(cursor.getString(3));
            Log.i("find All" , "records  " + cursor.getString(1) +
                    ", " + cursor.getString(2) + ", " +
                    cursor.getString(3));
            ar[i] = p;

        }

        return ar;
    }

    private void makePhoneCall(String phoneNumber)
    {
        try {
            Uri uri = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            Log.i("Phone Call App", phoneNumber);
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Application failed", Toast.LENGTH_SHORT).show();
        }
    }


}
