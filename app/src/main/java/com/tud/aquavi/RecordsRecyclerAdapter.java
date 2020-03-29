package com.tud.aquavi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tud.aquavi.tables.RecordInfo;

import java.util.List;

public class RecordsRecyclerAdapter extends RecyclerView.Adapter<RecordsRecyclerAdapter.ViewHolder>
{
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<RecordInfo> mRecords;

    public RecordsRecyclerAdapter(Context mContext, List<RecordInfo> mRecords)
    {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mRecords = mRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = mLayoutInflater.inflate(R.layout.item_record_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        RecordInfo recordInfo = mRecords.get(position);
        String drinkAndAmount = recordInfo.getQuantity() + " ml of " + recordInfo.getDrinkId();

        holder.textDate.setText(recordInfo.getDateId());
        holder.textAmount.setText(drinkAndAmount);

        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount()
    {
        return mRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        final TextView textDate;
        final TextView textAmount;
        int mCurrentPosition;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textAmount = itemView.findViewById(R.id.textAmount);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    RecordInfo recordInfo = mRecords.get(mCurrentPosition);
                    Bundle bundle = new Bundle();
                    System.out.println(mCurrentPosition + recordInfo.getDrinkId());
                    bundle.putString("drink_id", recordInfo.getDrinkId());
                    bundle.putString("date_id", recordInfo.getDateId());
                    bundle.putString("quantity", recordInfo.getQuantity());

                    Intent intent = new Intent(mContext, RecordActivity.class);
//                    intent.putExtra(recordInfo.getDrinkId(), mCurrentPosition);
                    intent.putExtras(bundle);

                    mContext.startActivity(intent);
                }
            });
        }
    }
}
