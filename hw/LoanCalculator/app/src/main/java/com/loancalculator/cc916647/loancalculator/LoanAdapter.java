package com.loancalculator.cc916647.loancalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cmcoffee91 on 3/7/18.
 */

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> {

    private List<AmortRow> mItemList;
    private Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView paymentNum;
        private TextView paymentAmt;
        private TextView towardsPrinciple;
        private TextView towardsInterest;
        private TextView remainingBal;



        public MyViewHolder(View view) {
            super(view);


            paymentNum = (TextView) view.findViewById(R.id.paymentNum);
            paymentAmt = (TextView) view.findViewById(R.id.paymentAmt);
            towardsPrinciple = (TextView) view.findViewById(R.id.towardsPrinciple);
            towardsInterest = (TextView) view.findViewById(R.id.towardsInterest);
            remainingBal = (TextView) view.findViewById(R.id.remainingBal);

        }
    }


    public void setData(List<AmortRow> itemList)
    {
        mItemList = itemList;
    }



    public LoanAdapter(List<AmortRow> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.amort_row_table, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        AmortRow item = mItemList.get(position);

        holder.paymentNum.setText( String.valueOf(item.getPaymentNum() ) );
        holder.paymentAmt.setText( String.format("$%,.2f", item.getPaymentAmt() ) );
        holder.towardsPrinciple.setText( String.format("$%,.2f", item.getTowardsPrinciple() ) );
        holder.towardsInterest.setText( String.format("$%,.2f", item.getTowardsInterest() ) );
        holder.remainingBal.setText( String.format("$%,.2f", item.getRemainingBalance() ) );

    }



    @Override
    public int getItemCount() {
        return mItemList.size();
    }


}
