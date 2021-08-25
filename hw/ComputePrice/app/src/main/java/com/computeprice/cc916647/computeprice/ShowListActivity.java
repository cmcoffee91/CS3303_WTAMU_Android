package com.computeprice.cc916647.computeprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity {

    private ShowListAdapter mAdapter;

    private int count = 0;

    private ArrayList<Item> mItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_show_list);

        count = getIntent().getIntExtra("count",0);
        mItemList = (ArrayList<Item>) getIntent().getSerializableExtra("itemList");

        RecyclerView mQuestionList = (RecyclerView) findViewById(R.id.showListRecyclerView);
        mQuestionList.setLayoutManager( new LinearLayoutManager(this) ) ;
        mAdapter = new ShowListAdapter(mItemList,  this);
        mQuestionList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();




     /*    LinearLayout.LayoutParams relParentParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout relParent = new LinearLayout(this);
        relParent.setLayoutParams(relParentParam);
        relParent.setOrientation(LinearLayout.VERTICAL);


        for(Item item:mItemList) {

            LinearLayout textContainers = new LinearLayout(this);
            textContainers.setLayoutParams(relParentParam);
            textContainers.setOrientation(LinearLayout.HORIZONTAL);

            TextView itemNameView = new TextView(this);
            TextView itemPriceView = new TextView(this);
            TextView itemQuantityView = new TextView(this);

            itemNameView.setText(item.getItemName());
            itemPriceView.setText(item.getItemPrice());
            itemQuantityView.setText(item.getItemQuantity());

            textContainers.addView(itemNameView);
            textContainers.addView(itemPriceView);
            textContainers.addView(itemQuantityView);
            relParent.addView(textContainers);


        }

        // Set parent RelativeLayout to your screen
        setContentView(relParent, relParentParam);

        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView1 = (SearchView) searchItem.getActionView();


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchQuerySubmit","query is : " + query);
                lookForItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchQueryOnTextChange","query textchange is : " + newText);
                return false;
            }
        });

        searchView1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("search","search closed");
              //  lookForItem("");
                mAdapter.setData(mItemList);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    private void lookForItem(String searchString)
    {
        ArrayList<Item> searchItems = new ArrayList<>();

        for(Item item:mItemList)
        {
            if(item.getItemName().contains(searchString))
            {
                searchItems.add(item);
            }
        }

        if(searchItems.size() > 0)
        {
            mAdapter.setData(searchItems);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this,"Sorry, that item was not found",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:


                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
