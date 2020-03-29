package com.tud.aquavi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tud.aquavi.database.DataManager;
import com.tud.aquavi.database.DatabaseOpenHelper;
import com.tud.aquavi.RecordsRecyclerAdapter;
import com.tud.aquavi.R;
import com.tud.aquavi.tables.RecordInfo;

import java.util.List;

public class RecordsFragment extends Fragment
{
    private static final String TAG = RecordsFragment.class.getSimpleName();

    private DatabaseOpenHelper databaseOpenHelper;
    private RecordsRecyclerAdapter recordsRecyclerAdapter;
    private View view;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_records, container, false);

        displayRecords();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        recordsRecyclerAdapter.notifyDataSetChanged();
        displayRecords();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        databaseOpenHelper.close();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private void displayRecords()
    {
        databaseOpenHelper = new DatabaseOpenHelper(getActivity());
        DataManager.loadFromDatabase(databaseOpenHelper);

        final RecyclerView recyclerView = view.findViewById(R.id.list_record);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        List<RecordInfo> records = DataManager.getInstance().getmRecords();
        recordsRecyclerAdapter = new RecordsRecyclerAdapter(getActivity(), records);

        recyclerView.setAdapter(recordsRecyclerAdapter);
    }
}