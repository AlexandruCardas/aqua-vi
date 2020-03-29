package com.tud.aquavi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.tud.aquavi.database.DataManager;
import com.tud.aquavi.database.DatabaseOpenHelper;
import com.tud.aquavi.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment
{
    private static final String TAG = HomeFragment.class.getSimpleName();

    private View view;
    private ArrayList<String> data = new ArrayList<>();
    private String spinnerData = null;
    private String listData = null;
    private DatabaseOpenHelper databaseOpenHelper;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseOpenHelper = new DatabaseOpenHelper(getContext());

        assert getArguments() != null;
        data = getArguments().getStringArrayList("my_amounts");

        Button btn = view.findViewById(R.id.add_drink);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (listData == null)
                {
                    final Snackbar snackBar = Snackbar.make(view, "You didn't chose anything yet",
                            Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            snackBar.dismiss();
                        }
                    }).show();
                }
                else
                {
                    String date = DataManager.getInstance().getDate(databaseOpenHelper);

                    DataManager.getInstance().insertDate(databaseOpenHelper, date);
                    DataManager.getInstance().insertRecord(databaseOpenHelper, date, spinnerData,
                            Integer.parseInt(listData));

                    final Snackbar snackBar = Snackbar.make(view, "Added drink of " +
                            spinnerData + " for " + listData + " ml", Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            snackBar.dismiss();
                        }
                    }).show();
                }
            }
        });

        displayInputs();

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

    private void displayInputs()
    {
        Spinner spinnerDrinks = view.findViewById(R.id.spinner1);
        List<String> drinks = DataManager.getInstance().getDrinkNames();

        ArrayAdapter<String> adapterDrinks = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, drinks);

        adapterDrinks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDrinks.setAdapter(adapterDrinks);

        final ListView listAmounts = view.findViewById(R.id.amount_list);

        ListAdapter adapterAmounts = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1, data);

        listAmounts.setAdapter(adapterAmounts);

        spinnerDrinks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                spinnerData = item.toString();
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Object item = parent.getItemAtPosition(0);
                spinnerData = item.toString();
            }
        });

        listAmounts.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l)
            {
                listData = adapterView.getItemAtPosition(index).toString();
            }
        });
    }
}