package com.tud.aquavi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tud.aquavi.database.DataManager;
import com.tud.aquavi.database.DatabaseOpenHelper;
import com.tud.aquavi.fragments.HomeFragment;
import com.tud.aquavi.fragments.RecordsFragment;
import com.tud.aquavi.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{
    FragmentManager manager;
    DatabaseOpenHelper databaseOpenHelper;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActionBar toolbar;
    ArrayList<String> listAmounts = new ArrayList<>();
    Bundle data = new Bundle();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "OnCreate()");
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        toolbar = getSupportActionBar();

        assert toolbar != null;
        toolbar.setTitle("Home");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("quantities").orderBy("storage")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Map<String, Object> data = document.getData();
                                listAmounts.add(data.get("storage").toString());

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                        else
                        {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        data.putStringArrayList("my_amounts", listAmounts);

//        deleteDatabase("AquaVi.db");

        replaceFragmentHome();
        databaseOpenHelper = new DatabaseOpenHelper(this);
        DataManager.loadFromDatabase(databaseOpenHelper);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_records:
                        toolbar.setTitle("Records");
                        replaceFragmentRecords();
                        break;
                    case R.id.action_home:
                        toolbar.setTitle("Home");
                        replaceFragmentHome();
                        break;
                    case R.id.action_settings:
                        toolbar.setTitle("Settings");
                        replaceFragmentSettings();
                        break;
                }
                return true;
            }
        });
    }

    public void replaceFragmentHome()
    {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(data);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, homeFragment, "fragHome");
        transaction.commit();
    }

    public void replaceFragmentRecords()
    {
        RecordsFragment recordsFragment = new RecordsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, recordsFragment, "fragRecords");
        transaction.commit();
    }

    public void replaceFragmentSettings()
    {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, settingsFragment, "fragSettings");
        transaction.commit();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
