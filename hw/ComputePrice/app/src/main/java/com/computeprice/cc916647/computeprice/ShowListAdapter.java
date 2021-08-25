package com.computeprice.cc916647.computeprice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cc916647 on 2/14/18.
 */

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.MyViewHolder> {

    private List<Item> mItemList;
    private Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private TextView itemQuantity;



        public MyViewHolder(View view) {
            super(view);


            itemName = (TextView) view.findViewById(R.id.itemName);
            itemPrice = (TextView) view.findViewById(R.id.itemPrice);
            itemQuantity = (TextView) view.findViewById(R.id.itemQuantity);

        }
    }


    public void setData(List<Item> itemList)
    {
        mItemList = itemList;
    }



    public ShowListAdapter(List<Item> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_list_adapter, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Item item = mItemList.get(position);

        holder.itemName.setText( item.getItemName() );
        holder.itemPrice.setText( item.getItemPrice() );
        holder.itemQuantity.setText( item.getItemQuantity() );

    }



    @Override
    public int getItemCount() {
        return mItemList.size();
    }


}
