package com.loancalculator.cc916647.loancalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LoanTableActivity extends AppCompatActivity {


    private List amortItems ;
    private LoanAdapter mAdapter;
    private double totalInterest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_table);

        /*
        intent.putExtra("loanAmt", loanAmt.getText().toString());
                intent.putExtra("loanAPR", loanAPR.getText().toString());
                intent.putExtra("loanTerm", loanTerm.getText().toString());
         */

        TextView loanAmtView = (TextView) findViewById(R.id.loanAmtView);
        TextView numOfPayment = (TextView) findViewById(R.id.numOfPayments);

        double amount = getIntent().getDoubleExtra("loanAmt",0.0);
        int years = getIntent().getIntExtra("loanTerm",0);
        double apr = getIntent().getDoubleExtra("loanAPR",0.0);

        double rate = ( (apr/100) / 12 );
        int months = years * 12;
        double payment = amount * (rate + ( rate / (Math.pow(1+rate,months) - 1) ) );

        loanAmtView.setText("Your loan amount: " + String.format("$%,.2f", amount));
        numOfPayment.setText("Your number of payments: " + months);

        double remainingBalance = amount;


        totalInterest = 0;

        amortItems = new ArrayList();

        for(int i = 0; i < months; i++)
        {



            double towardsInterest = rate * remainingBalance;
            double towardsPrinciple = payment - towardsInterest;

            totalInterest = totalInterest + towardsInterest;

            remainingBalance = remainingBalance - towardsPrinciple;

            AmortRow amortRow = new AmortRow(i+1, payment, towardsPrinciple,towardsInterest, remainingBalance);
            amortItems.add(amortRow);
        }

        Intent data = new Intent();
        data.putExtra("totalInterest",totalInterest);
        setResult(Activity.RESULT_OK, data);


        RecyclerView mQuestionList = (RecyclerView) findViewById(R.id.amortRecyclerView);
        mQuestionList.setLayoutManager( new LinearLayoutManager(this) ) ;
        mAdapter = new LoanAdapter(amortItems,  this);
        mQuestionList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();




    }
}
