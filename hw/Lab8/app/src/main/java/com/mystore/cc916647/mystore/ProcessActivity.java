package com.mystore.cc916647.mystore;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        Context context = ProcessActivity.this;
        TableLayout rootLayout = findViewById(R.id.activityProcessRoot);

        MyDBHandler dbHandler = new MyDBHandler(context);
        StoreItem[] items = dbHandler.findAll();

        double subTotal = 0;
        double tax = 0.0825;

        TableRow.LayoutParams textViewLineParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        textViewLineParams.height = convertToPx(1);

        TextView divider = new TextView(context);
        divider.setLayoutParams(textViewLineParams);
        divider.setBackgroundColor(Color.BLACK);
        divider.setText("");
        rootLayout.addView(divider);

        TableLayout.LayoutParams rowHolderParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        //layout params must use it's parent's "type" params
        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        textViewParams.weight = 1;


        for(int i = 0; i < items.length; i++)
        {


            TableRow rowHolder = new TableRow(context);
            rowHolder.setOrientation(TableRow.HORIZONTAL);

            rowHolder.setLayoutParams(rowHolderParams);




            TextView itemName = new TextView(context);
            itemName.setLayoutParams(textViewParams);


            TextView itemQuantity = new TextView(context);
            itemQuantity.setLayoutParams(textViewParams);

            itemName.setText(items[i].getName());
            itemQuantity.setText(String.valueOf(items[i].getQuantity()));

            TextView itemPrice = new TextView(context);
            itemPrice.setText(String.format("$%,.2f", items[i].getPrice()));
            itemPrice.setLayoutParams(textViewParams);

            rowHolder.addView(itemName);
            rowHolder.addView(itemQuantity);
            rowHolder.addView(itemPrice);

            rootLayout.addView(rowHolder);

            subTotal = subTotal + items[i].getPrice();


        }



        TextView line = new TextView(context);
        line.setBackgroundColor(Color.BLACK);
        line.setText("");
        line.setLayoutParams(textViewLineParams);
        rootLayout.addView(line);



        TableRow subtotalRow = new TableRow(context);
        subtotalRow.setLayoutParams(rowHolderParams);

        TextView filler = new TextView(context);
        filler.setLayoutParams(textViewParams);
        filler.setText("");

        TextView subTotalView = new TextView(context);
        subTotalView.setLayoutParams(textViewParams);
        subTotalView.setText("Subtotal");

        TextView subTotalValueView = new TextView(context);
        subTotalValueView.setLayoutParams(textViewParams);
        subTotalValueView.setText(String.format("$%,.2f", subTotal));

        subtotalRow.addView(subTotalView);
        subtotalRow.addView(filler);
        subtotalRow.addView(subTotalValueView);
        rootLayout.addView(subtotalRow);


        TextView filler2 = new TextView(context);
        filler2.setLayoutParams(textViewParams);
        filler2.setText("");

        TableRow taxRow = new TableRow(context);
        taxRow.setLayoutParams(rowHolderParams);

        TextView taxView = new TextView(context);
        taxView.setLayoutParams(textViewParams);
        taxView.setText("Tax (8.25%)");

        TextView taxValueView = new TextView(context);
        taxValueView.setLayoutParams(textViewParams);
        taxValueView.setText(String.format("$%,.2f", tax * subTotal));

        taxRow.addView(taxView);
        taxRow.addView(filler2);
        taxRow.addView(taxValueView);
        rootLayout.addView(taxRow);


        TextView filler3 = new TextView(context);
        filler3.setLayoutParams(textViewParams);
        filler3.setText("");

        TableRow totalRow = new TableRow(context);
        totalRow.setLayoutParams(rowHolderParams);

        TextView totalView = new TextView(context);
        totalView.setLayoutParams(textViewParams);
        totalView.setText("Total");

        TextView totalValueView = new TextView(context);
        totalValueView.setLayoutParams(textViewParams);
        totalValueView.setText(String.format("$%,.2f", (tax * subTotal) + subTotal));

        totalRow.addView(totalView);
        totalRow.addView(filler3);
        totalRow.addView(totalValueView);
        rootLayout.addView(totalRow);






    }



    //pixels were the old android way
    public int convertToPx(int dp) {

        /*
        https://developer.android.com/training/multiscreen/screendensities.html#TaskUseDP
         */

        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }


}
