package com.loancalculator.cc916647.loancalculator;

import android.app.Activity;
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


/*
Authors: Chris Coffee and Bryan Polanco
Both of us contributed layout, bug fixes, and development of app equally, 50/50,
on each activity.  50 % each.

03/07/2018


 */



public class MainActivity extends AppCompatActivity {


    EditText loanAmt , loanAPR, loanTerm, paymentAmt;
    TextView totalPriceView;

    Button resetButton, calculateButton, amortButton;
    Double amount, apr;
    int years;


    private final int AMORT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loanAmt = (EditText) findViewById(R.id.loanAmt);

        loanAPR = (EditText) findViewById(R.id.loanApr);

        loanTerm = (EditText) findViewById(R.id.loanTerm);

        totalPriceView  = (TextView) findViewById(R.id.interestPaidTextView);
        totalPriceView.setVisibility(View.GONE);




        paymentAmt = (EditText) findViewById(R.id.paymentAmt);
        //could use textview instead
        paymentAmt.setFocusable(false);

        amortButton = (Button) findViewById(R.id.amortButton);
        amortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , LoanTableActivity.class);
                intent.putExtra("loanAmt", amount);
                intent.putExtra("loanAPR", apr);
                intent.putExtra("loanTerm", years);


                startActivityForResult(intent, AMORT_REQUEST_CODE);

            }
        });



        resetButton = (Button) findViewById(R.id.resetButton);

        calculateButton = (Button) findViewById(R.id.calcButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboards();

                //loanAmt , loanAPR, loanTerm

                if (loanAmt.getText().toString().isEmpty() || loanAPR.getText().toString().isEmpty() || loanTerm.getText().toString().isEmpty()){
                    // Toast.makeText(MainActivity.this, "Please fill in missing values",
                    //        Toast.LENGTH_LONG).show();

                    showAlert();
                }
                else
                {



                    v.setEnabled(false);
                    resetButton.setEnabled(true);
                    amortButton.setEnabled(true);



                    amount = Double.parseDouble(loanAmt.getText().toString());
                    years = Integer.parseInt(loanTerm.getText().toString());
                    apr = Double.parseDouble(loanAPR.getText().toString());

                    double rate = ( (apr/100) / 12 );
                    int months = years * 12;

                    double payment = amount * (rate + ( rate / (Math.pow(1+rate,months) - 1) ) );

                    paymentAmt.setText(String.format("$%,.2f", payment));
                    loanAmt.setText(String.format("$%,.2f", amount));
                    loanAPR.setText(String.format("%5.2f", apr) + "%");
                    loanTerm.setText(String.format("%3.0f", (double)years ));

                }

            }
        });



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);
                amortButton.setEnabled(false);

                calculateButton.setEnabled(true);
                totalPriceView.setText("");


                loanAmt.setText("");
                loanTerm.setText("");
                loanAPR.setText("");
                paymentAmt.setText("");



                totalPriceView.setText("");
                loanAmt.requestFocus();



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



        if (requestCode == AMORT_REQUEST_CODE) {
            if (data == null) {
                return;
            }

            totalPriceView.setVisibility(View.VISIBLE);
            totalPriceView.setText("Over the period of the loan interest paid: " + String.format("$%,.2f",data.getDoubleExtra("totalInterest",0)));

        }
    }



}
