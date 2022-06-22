package com.example.kharchapani;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


//public class RecordAdapter implements ListAdapter{
//
//    ArrayList<Record> record_arraylist = new ArrayList<>();
//     Context context;
//    TextView category;
//    TextView amount;
//
//
//    public RecordAdapter(ArrayList<Record> record_arraylist, Context context) {
//        this.record_arraylist = record_arraylist;
//        this.context = context;
//    }
//
//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled(int i) {
//        return true;
//    }
//
//    @Override
//    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
//
//    }
//
//    @Override
//    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
//
//    }
//
//    @Override
//    public int getCount() {
//        return record_arraylist.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return i;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        Record record = record_arraylist.get(i);
//        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
//        view = layoutInflater.inflate(R.layout.record_item, viewGroup, false);
//         category = view.findViewById(R.id.rv_categ);
//        amount = view.findViewById(R.id.rv_amt);
//        category.setText(record.getCategory());
//
//        Toast.makeText(context, ""+record.getAmmount(), Toast.LENGTH_SHORT).show();
//       amount.setText("$"+record.getAmmount());
//
//        return view;
//    }
//
//    @Override
//    public int getItemViewType(int i) {
//        return i;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return record_arraylist.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//}

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder>  {

    ArrayList<Record> record_arraylist = new ArrayList<>();
    Context context;

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView category;
        TextView amount;
        TextView account;
        TextView openingbal, closingbal;
        View view;
        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.rv_categ);
            amount = itemView.findViewById(R.id.rv_amt);
            account = itemView.findViewById(R.id.rv_accn);
            openingbal = itemView.findViewById(R.id.rv_opbal);
            closingbal = itemView.findViewById(R.id.rv_closbal);
            view = itemView;
        }
    }

    public RecordAdapter(ArrayList<Record> record_arraylist, Context ctx) {
        this.record_arraylist = record_arraylist;
        this.context = ctx;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recordView = layoutInflater.inflate(R.layout.record_item,parent,false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(recordView);
        return recordViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.category.setText(record_arraylist.get(position).getCategory());
        holder.amount.setText("₹"+record_arraylist.get(position).getAmmount());
        holder.account.setText(record_arraylist.get(position).getAccount());
        holder.openingbal.setText("Opening Balance : "+record_arraylist.get(position).getOpeningbal());
        holder.closingbal.setText("Closing Balance : "+record_arraylist.get(position).getClosingbal());

    }

    @Override
    public int getItemCount() {
        return record_arraylist.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
