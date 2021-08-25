package com.mystore.cc916647.mystore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
Chris Coffee and Bryan Polanco evenly split the ui and logic code
in the app 50%/50%.

Chris Coffee's contribution:
AddItemActivity
StoreActivity
ProcessActivity
StoreItemInfo


Bryan Polanco's contribution:
MyDBHandler
ProcessActivity
StoreActivity
StoreItem




Lab8

April 12, 2018
 */

public class StoreActivity extends AppCompatActivity {


    private RecyclerView mStoreInfoRecyclerView;
    private StoreItemAdapter mAdapter;

    private final int ADD_ITEM_REQUEST_CODE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        Context context = StoreActivity.this;
        LinearLayout rootLayout = findViewById(R.id.activityStoreRoot);



        final List<StoreItemInfo> infoList = new ArrayList<>();


        StoreItemInfo itemInfo = new StoreItemInfo();

        itemInfo.setName("Dasani Water");
        itemInfo.setPrice(2.00);
        itemInfo.setImageId(R.drawable.dasani_water);
        infoList.add(itemInfo);

        itemInfo = new StoreItemInfo();
        itemInfo.setName("Fruit Oatmeal");
        itemInfo.setPrice(2.00);
        itemInfo.setImageId(R.drawable.fruit_maple_oatmeal);
        infoList.add(itemInfo);

        itemInfo = new StoreItemInfo();
        itemInfo.setName("Hotcakes");
        itemInfo.setPrice(2.00);
        itemInfo.setImageId(R.drawable.hotcakes);
        infoList.add(itemInfo);

        itemInfo = new StoreItemInfo();
        itemInfo.setName("Sausage Biscuit");
        itemInfo.setPrice(1.99);
        itemInfo.setImageId(R.drawable.sausage_biscuit);
        infoList.add(itemInfo);

        itemInfo = new StoreItemInfo();
        itemInfo.setName("Bacon Egg Biscuit");
        itemInfo.setPrice(2.00);
        itemInfo.setImageId(R.drawable.bec_biscuit);
        infoList.add(itemInfo);

        itemInfo = new StoreItemInfo();
        itemInfo.setName("Egg Sausage");
        itemInfo.setPrice(2.00);
        itemInfo.setImageId(R.drawable.sec_biscuit);
        infoList.add(itemInfo);

        itemInfo = new StoreItemInfo();
        itemInfo.setName("Sausage Burrito");
        itemInfo.setPrice(1.75);
        itemInfo.setImageId(R.drawable.sausage_burrito);
        infoList.add(itemInfo);


        for(int i = 0; i < infoList.size();i++)
        {
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);

            int leftMargin = convertToPx(5);

            //layout params must use it's parent's "type" params
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //imageParams.weight = 1;
            //imageParams.width = convertToPx(100);

            ImageButton imageButton = new ImageButton(context);
            imageButton.setImageDrawable(context.getResources().getDrawable(infoList.get(i).getImageId()));
            imageButton.setLayoutParams(imageParams);
            final String chosenItemName = infoList.get(i).getName();
            final double chosenItemPrice = infoList.get(i).getPrice();
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StoreActivity.this , AddItemActivity.class);
                    intent.putExtra("chosenItemName", chosenItemName);
                    intent.putExtra("chosenItemPrice", chosenItemPrice);
                    startActivity(intent);
                }
            });


            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(leftMargin,0,0,0);
            textViewParams.gravity = Gravity.CENTER;


            TextView itemName = new TextView(context);
            itemName.setText(infoList.get(i).getName() + "\n" + String.format("$%,.2f", infoList.get(i).getPrice()));
            itemName.setLayoutParams(textViewParams);


            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dividerParams.height = convertToPx(1);

            TextView divider = new TextView(context);
            divider.setBackgroundColor(Color.BLACK);
            divider.setLayoutParams(dividerParams);

            row.addView(imageButton);
            row.addView(itemName);
            if(i>0)rootLayout.addView(divider);
            rootLayout.addView(row);
        }

        Button processButton = new Button(context);
        processButton.setText("Process Order");
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this , ProcessActivity.class);
                startActivity(intent);
            }
        });

        rootLayout.addView(processButton);




        /*
        mStoreInfoRecyclerView = (RecyclerView) findViewById(R.id.storeInfoRecyclerView);
        mStoreInfoRecyclerView.setLayoutManager(new LinearLayoutManager(StoreActivity.this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(StoreActivity.this,DividerItemDecoration.VERTICAL);
        mStoreInfoRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new StoreItemAdapter(infoList);
        mStoreInfoRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        */
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






    /******************************************************************
     *
     * store info recyclerview
     *
     * ***************************************************************/


    private class StoreItemAdapter extends RecyclerView.Adapter<StoreViewHolder>
    {
        private List<StoreItemInfo> mItemList;

        public StoreItemAdapter(List<StoreItemInfo> itemList)
        {
            mItemList = itemList;
        }


        @Override
        public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoreActivity.this);
            return new StoreViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(StoreViewHolder holder, int position) {
            StoreItemInfo itemInfo = mItemList.get(position);
            holder.bind(itemInfo);

        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }


    private class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private StoreItemInfo mInfo;

        private ImageView itemImage;
        private TextView itemPrice, itemName;

        public StoreViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.store_row, parent,false));

            itemView.setOnClickListener(this);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemName = itemView.findViewById(R.id.itemName);



        }

        public void bind(StoreItemInfo info)
        {
            mInfo = info;
            itemName.setText(mInfo.getName());
            itemPrice.setText(String.valueOf(mInfo.getPrice()));

            Context context = StoreActivity.this;

            int resourceId = context.getResources().getIdentifier(mInfo.getName(), "drawable",
                    context.getPackageName());

            itemImage.setImageDrawable(context.getResources().getDrawable(mInfo.getImageId()));
        }


        @Override
        public void onClick(View v) {

            Intent intent = new Intent(StoreActivity.this , AddItemActivity.class);
            intent.putExtra("chosenItemName", mInfo.getName());
            startActivity(intent);
        }
    }



}
