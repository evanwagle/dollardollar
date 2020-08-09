package com.example.saver;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<PaidItem> paidItems;

    public MainAdapter(List<PaidItem> paidItems) {
        this.paidItems = paidItems;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        String description = paidItems.get(position).getDescription();
        String cost = paidItems.get(position).getCost();

        holder.costView.setText(description);
        holder.descView.setText(cost);
    }

    @Override
    public int getItemCount() {
        return paidItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView costView;
        public TextView descView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            costView = itemView.findViewById(R.id.row_desc);
            descView = itemView.findViewById(R.id.row_amount);

            // OnClickListener for each item in the RecyclerView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), EditActivity.class);
                    ListContainer.position = getAdapterPosition();
                    view.getContext().startActivity(myIntent);
                }
            });
        }
    }

}
