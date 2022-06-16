package com.example.kharchapani;

import android.content.Context;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordViewAdapter extends RecyclerView.Adapter<RecordViewAdapter.RecordView_ViewHolder> {

    ArrayList<RecordsView> titlelist = new ArrayList<>();
    Context cxt;
    public RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public class RecordView_ViewHolder extends RecyclerView.ViewHolder {


        public TextView datetitle;
        public RecyclerView ChildRecyclerView;

        View view;

        RecordView_ViewHolder(@NonNull View itemView) {
            super(itemView);

            datetitle
                    = itemView
                    .findViewById(
                            R.id.parent_item_title);
            ChildRecyclerView
                    = itemView
                    .findViewById(
                            R.id.child_recyclerview);

            view = itemView;


        }

    }


    public RecordViewAdapter(ArrayList<RecordsView> titlelist, Context cxt) {
        this.titlelist = titlelist;
        this.cxt = cxt;
    }

    @NonNull
    @Override
    public RecordView_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.records_view,
                        parent, false);

        return new RecordView_ViewHolder(view);

    }




    @Override
    public void onBindViewHolder(@NonNull RecordView_ViewHolder holder, int position) {

        DateFormatter date = new DateFormatter();
        holder.datetitle.setText(date.dateformat(titlelist.get(position).getDatetitle()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(cxt,
                LinearLayoutManager.VERTICAL,
                false);
       layoutManager.setInitialPrefetchItemCount(titlelist.get(position).getChildItemRecords().size());

//        RecordAdapter recordAdapter = new RecordAdapter(titlelist.get(position).getChildItemRecords(),holder.ChildRecyclerView.getContext());
//        holder.ChildRecyclerView.setAdapter(recordAdapter);

            holder.ChildRecyclerView.setLayoutManager(layoutManager);

        ArrayList<Record> childrecordlist = new ArrayList<>();
        childrecordlist = titlelist.get(position).ChildItemRecords;
       RecordAdapter adapter = new RecordAdapter(childrecordlist, holder.ChildRecyclerView.getContext());
            holder.ChildRecyclerView.setAdapter(adapter);
            holder.ChildRecyclerView.setRecycledViewPool(viewPool);


//        RecordsView obj = new RecordsView();
//       RecordAdapter RecordAdapter = new RecordAdapter(holder.bind(titlelist.get(position)));
//        RecordView_ViewHolder
//               .ChildRecyclerView
//               .setLayoutManager(layoutManager);
//        RecordView_ViewHolder
//                .ChildRecyclerView
//               .setAdapter(RecordAdapter);
//                RecordView_ViewHolder
//                .ChildRecyclerView
//               .setRecycledViewPool(viewPool);
   }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }




}



