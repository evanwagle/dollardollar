package com.example.saver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class HomeFragment extends Fragment {

    RecyclerView myPurchaseRecycler;
    LinearLayoutManager myLayoutManager;
    public static RecyclerView.Adapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Setup for Extended FAB */
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab);
        fab.bringToFront();
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(HomeFragment.this.getActivity(), AddPurchaseActivity.class);
                HomeFragment.this.startActivity(homeIntent);
            }
        });
        Context context = getActivity();

        // Recycler setup
        myPurchaseRecycler = view.findViewById(R.id.recycler_view);
        myPurchaseRecycler.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(context);
        // Displays items in reverse order and stacks latest items on top
        myLayoutManager.setReverseLayout(true);
        myLayoutManager.setStackFromEnd(true);

        myAdapter = new MainAdapter(ListContainer.paidItems);
        myPurchaseRecycler.setAdapter(myAdapter);
        myPurchaseRecycler.setLayoutManager(myLayoutManager);

        return view;
    }

}
