package com.tud.aquavi.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.tud.aquavi.database.DataManager;
import com.tud.aquavi.database.DatabaseOpenHelper;
import com.tud.aquavi.R;

public class SettingsFragment extends Fragment
{
    private static final String TAG = SettingsFragment.class.getSimpleName();

    private SeekBar sBar;
    private TextView tView;
    private TextView height;
    private TextView weight;
    private DatabaseOpenHelper databaseOpenHelper;
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
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        databaseOpenHelper = new DatabaseOpenHelper(getContext());

        tView = view.findViewById(R.id.progressBar);
        height = view.findViewById(R.id.editHeight);
        weight = view.findViewById(R.id.editWeight);
        sBar = view.findViewById(R.id.seekBar);
        Button btn = view.findViewById(R.id.saveButton);

        Cursor cursor = DataManager.getInstance().getUser(databaseOpenHelper);
        cursor.moveToFirst();

        final String userWeight = cursor.getString(2);
        final String userHeight = cursor.getString(3);
        final String userGoal = cursor.getString(4);

        height.setText(userHeight);
        weight.setText(userWeight);
        sBar.setProgress(Integer.parseInt(userGoal));

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                progressValue = progress;
                String result = progressValue + "/" + seekBar.getMax();
                tView.setText(result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        btn.setOnClickListener(new View.OnClickListener()
        {
            private Snackbar snackBar;

            @Override
            public void onClick(View v)
            {
                String newWeight = weight.getText().toString();
                String newHeight = height.getText().toString();
                String newGoal = String.valueOf(sBar.getProgress());

                if (Integer.parseInt(newHeight) > 230 || Integer.parseInt(newWeight) > 200)
                {
                    snackBar = Snackbar.make(view, "Max weight = 200 and Max Height = 230", Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            snackBar.dismiss();
                        }
                    }).show();
                }
                else if (Integer.parseInt(newHeight) < 0 || Integer.parseInt(newWeight) < 0)
                {
                    snackBar = Snackbar.make(view, "Cannot have negative values", Snackbar.LENGTH_LONG);

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
                    snackBar = Snackbar.make(view, "Records Updated!", Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            snackBar.dismiss();
                        }
                    }).show();

                    DataManager.getInstance().updateUser(databaseOpenHelper, newWeight, newHeight, newGoal);

                    Cursor cursor = DataManager.getInstance().getUser(databaseOpenHelper);
                    cursor.moveToFirst();

                    weight.setText(newWeight);
                    height.setText(newHeight);
                    sBar.setProgress(Integer.parseInt(newGoal));
                }
            }
        });

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
}