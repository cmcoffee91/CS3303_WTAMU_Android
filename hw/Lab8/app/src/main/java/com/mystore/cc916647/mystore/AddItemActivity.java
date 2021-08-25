package com.mystore.cc916647.mystore;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {


    private String theFinalText = "";
    EditText quantityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Context context = AddItemActivity.this;


        LinearLayout rootLayout = findViewById(R.id.containerLayout);
        TextView currentItemView = findViewById(R.id.currentItemView);
        quantityView = findViewById(R.id.quantityView);


        currentItemView.setText(getIntent().getExtras().getString("chosenItemName"));

        ScrollView scrollView = new ScrollView(context);
        scrollView.setFillViewport(true);

        TableLayout container = new TableLayout(context);
        container.setOrientation(TableLayout.VERTICAL);
        scrollView.addView(container);

        MyDBHandler dbHandler = new MyDBHandler(context);
        StoreItem[] items = dbHandler.findAll();

        for(int i = 0; i < items.length; i++)
        {


            TableRow rowHolder = new TableRow(context);
            rowHolder.setOrientation(TableRow.HORIZONTAL);

            //layout params must use it's parent's "type" params
            TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

            textViewParams.weight=1;

            TextView itemName = new TextView(context);
            itemName.setLayoutParams(textViewParams);

            TextView itemQuantity = new TextView(context);
            itemQuantity.setLayoutParams(textViewParams);

            itemName.setText(items[i].getName());
            itemQuantity.setText(String.valueOf(items[i].getQuantity()));

            rowHolder.addView(itemName);
            rowHolder.addView(itemQuantity);

            container.addView(rowHolder);


        }

        rootLayout.addView(scrollView);


    }


    @Override
    public void onBackPressed() {


        int itemQuantity = 0;
        if(!quantityView.getText().toString().isEmpty())
        {
            itemQuantity = Integer.parseInt(quantityView.getText().toString());
        }


        if(itemQuantity > 0 ) {
            String itemName = getIntent().getExtras().getString("chosenItemName");
            Double itemPrice = getIntent().getExtras().getDouble("chosenItemPrice");

            StoreItem item = new StoreItem();
            item.setName(itemName);
            item.setQuantity(itemQuantity);
            item.setPrice(itemPrice * itemQuantity);

            MyDBHandler dbHandler = new MyDBHandler(AddItemActivity.this);
            dbHandler.addProduct(item);
        }


        //finishes activity
        super.onBackPressed();
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
