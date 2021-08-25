package com.phonebook.cc916647.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
Members in group :
Chris Coffee
Bryan Polanco

We both contributed an equal amount of work, 50/50,
on this app.

Bryan Polanco:
MainActivity
MyContentProvider
ShowAllActivity


Chris Coffee
MainActivity
MyContentProvider
MyDbHandler
 */

public class MainActivity extends AppCompatActivity {

    //todo: maybe put all the constants in one class
    private static final String CONTENT_AUTHORITY = "com.phonebook.cc916647.phonebook.MyContentProvider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_CONTACTS = "contacts";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CONTACTS );

    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        final EditText lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberText);

        Button addButton = (Button) findViewById(R.id.addButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        final Button showButton = (Button) findViewById(R.id.showButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();
                String phoneNum = phoneNumber.getText().toString();

                //todo: maybe check if phone num has 10 digits?

                if( !firstName.isEmpty() && !lastName.isEmpty() && !phoneNum.isEmpty() )
                {
                    Context context = MainActivity.this;

                    ContentValues value = new ContentValues();
                    value.put(COLUMN_FIRST_NAME, firstName);
                    value.put(COLUMN_LAST_NAME, lastName);
                    value.put(COLUMN_PHONE, phoneNum);

                    Uri newUri = context.getContentResolver().insert(CONTENT_URI, value);

                    //when the uri is returned as null, the insert wasnt successful
                    if(newUri != null)
                    {
                        Toast.makeText(context, "The contact was successfully added!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context, "The contact was NOT successfully added! Please try again.", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter info in all fields!", Toast.LENGTH_LONG).show();
                }


                showButton.setEnabled(true);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();

                //todo: maybe check if phone num has 10 digits?

                if( !firstName.isEmpty() && !lastName.isEmpty() )
                {
                    Context context = MainActivity.this;


                    int deleteCount = getContentResolver( ).delete(CONTENT_URI , null, new String[]{firstName,lastName});

                    //check if delete was successful
                    if(deleteCount > 0)
                    {
                        Toast.makeText(context, "The contact was successfully deleted!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context, "The contact was NOT successfully deleted! Please try again.", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter first and last name!", Toast.LENGTH_LONG).show();
                }
            }
        });


        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showAllIntent = new Intent(MainActivity.this, ShowAllActivity.class);
                startActivity(showAllIntent);

            }
        });


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNameEdit.setText("");
                lastNameEdit.setText("");
                phoneNumber.setText("");

                firstNameEdit.requestFocus();
            }
        });
    }
}
