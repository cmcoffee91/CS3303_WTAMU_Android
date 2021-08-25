package com.computeprice.cc916647.computeprice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    private int count = 0;

    private ArrayList<Item> itemList;

    TextView itemCountView;
    EditText itemName, itemPrice, itemQuantity;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        count = getIntent().getIntExtra("count",0);
        itemList = (ArrayList<Item>) getIntent().getSerializableExtra("itemList");

        itemCountView = (TextView) findViewById(R.id.itemCount);
        itemCountView.setText("Number of item(s): " + count);

        itemName = (EditText) findViewById(R.id.itemName);


        itemPrice = (EditText) findViewById(R.id.itemPrice);

        itemQuantity = (EditText) findViewById(R.id.itemQuantity);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setEnabled(false);
                closeKeyboards();

                String itemNameValue = itemName.getText().toString();
                String itemPriceValue = itemPrice.getText().toString();
                String itemQuantityValue = itemQuantity.getText().toString();

                if(itemNameValue.isEmpty() || itemPriceValue.isEmpty() || itemQuantityValue.isEmpty())
                {
                    showAlert();
                }
                else
                {
                    count++;

                    Log.e("AddItem", "itemName added: " + itemNameValue);
                    Log.e("AddItem", "itemPriceValue added: " + itemPriceValue);
                    Log.e("AddItem", "itemQuantityValue added: " + itemQuantityValue);



                    Item item = new Item(itemNameValue, itemPriceValue, itemQuantityValue);
                    itemList.add(item);

                    Intent data = new Intent();
                    data.putExtra("updatedList", itemList);
                    data.putExtra("updatedCount", count);
                    setResult(Activity.RESULT_OK , data );

                    //finish();
                    showSuccessAlert();
                }
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

    private void showSuccessAlert()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Success!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Item successfully added!")
                .setCancelable(false)
                .setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                })
        .setNegativeButton("Add Item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                itemName.setText("");
                itemPrice.setText("");
                itemQuantity.setText("");
                itemCountView.setText("Number of item(s): " + count);
                itemName.requestFocus();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }







    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
