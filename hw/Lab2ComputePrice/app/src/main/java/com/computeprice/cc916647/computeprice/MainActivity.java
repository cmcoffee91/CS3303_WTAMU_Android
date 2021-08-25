package com.computeprice.cc916647.computeprice;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText itemPriceView , quantityView, taxView;
    TextView totalPriceView;

    Button resetButton, calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemPriceView = (EditText) findViewById(R.id.itemPrice);

        quantityView = (EditText) findViewById(R.id.quantity);

        taxView = (EditText) findViewById(R.id.tax);

        totalPriceView  = (TextView) findViewById(R.id.totalPrice);



        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboards();


                if (itemPriceView.getText().toString().isEmpty() || quantityView.getText().toString().isEmpty() || taxView.getText().toString().isEmpty()){
                    // Toast.makeText(MainActivity.this, "Please fill in missing values",
                    //        Toast.LENGTH_LONG).show();

                    showAlert();
                }
                else {
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

                itemPriceView.requestFocus();

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
}
