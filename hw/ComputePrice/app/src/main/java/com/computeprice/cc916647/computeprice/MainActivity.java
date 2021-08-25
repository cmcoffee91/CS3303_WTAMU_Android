package com.computeprice.cc916647.computeprice;

/*
Authors: Chris Coffee and Bryan Polanco
Both of us contributed layout, bug fixes, and development of app equally, 50/50,
on each activity.

02/23/2018

This app allows you to compute sales tax. It also lets you add items to a list
and search that list.


 */

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText itemPriceView , quantityView, taxView, itemNameView;
    TextView totalPriceView;
    ArrayList<Item> itemList;

    private int count = 0;

    private final int ADD_ITEM_REQUEST_CODE = 1;
    private final int SHOW_LIST_REQUEST_CODE = 2;

    Button resetButton, calculateButton, addItem, showList, takePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemPriceView = (EditText) findViewById(R.id.itemPrice);

        quantityView = (EditText) findViewById(R.id.quantity);

        taxView = (EditText) findViewById(R.id.tax);

        totalPriceView  = (TextView) findViewById(R.id.totalPrice);

        itemNameView = (EditText) findViewById(R.id.itemName);


        itemList = new ArrayList<>();


        addItem = (Button) findViewById(R.id.addItemButton);
        addItem.setEnabled(false);
        addItem.setBackgroundColor(Color.LTGRAY);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , AddItemActivity.class);
                intent.putExtra("count", count);
                intent.putExtra("itemList", itemList);

                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);

            }
        });

        showList = (Button) findViewById(R.id.showList);
        showList.setEnabled(false);
        showList.setBackgroundColor(Color.LTGRAY);
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , ShowListActivity.class);
                intent.putExtra("count", count);
                intent.putExtra("itemList", itemList);

                startActivity(intent);

            }
        });




        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboards();


                if (itemPriceView.getText().toString().isEmpty() || quantityView.getText().toString().isEmpty() || taxView.getText().toString().isEmpty() || itemNameView.getText().toString().isEmpty() ){
                    // Toast.makeText(MainActivity.this, "Please fill in missing values",
                    //        Toast.LENGTH_LONG).show();

                    showAlert();
                }
                else
                {

                    //todo: handle only when item added
                    showList.setEnabled(true);

                    addItem.setEnabled(true);
                    addItem.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    showList.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    v.setEnabled(false);
                    //Formula: total = price*qty + (rounded two decimal precision) price*qty*tax_rate/100

                    double itemPrice = Double.parseDouble(itemPriceView.getText().toString());
                    double quantity = Double.parseDouble(quantityView.getText().toString());
                    double tax = Double.parseDouble(taxView.getText().toString());

                    double result = (itemPrice * quantity) + (itemPrice * quantity * tax)/100 ;

                    totalPriceView.setText(String.format("$%,.2f", result));

                    itemPriceView.setText(String.format("$%,.2f", itemPrice));
                    quantityView.setText(String.format("%2.0f", quantity ));
                    taxView.setText(String.format("%5.2f", tax));
                }

            }
        });


        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateButton.setEnabled(true);
                totalPriceView.setText("");
                quantityView.setText("");
                itemPriceView.setText("");
                taxView.setText("");
                itemNameView.setText("");


                itemNameView.requestFocus();

            }
        });



        

    }



    public void closeKeyboards()
    {
        //  Log.e("closeKeyboards", "keyboards");

        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }

    }


    private void showAlert()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Missing Field Values");

        // set dialog message
        alertDialogBuilder
                .setMessage("Please fill in the missing fields.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        //todo: maybe should use switch



        if (requestCode == ADD_ITEM_REQUEST_CODE) {
            if (data == null) {
                return;
            }

            itemList = (ArrayList<Item>) data.getSerializableExtra("updatedList");
            count = data.getIntExtra("updatedCount",0);

        }
    }


}
