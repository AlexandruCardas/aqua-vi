package com.tud.aquavi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tud.aquavi.database.DataManager;
import com.tud.aquavi.database.DatabaseOpenHelper;


public class RecordActivity extends AppCompatActivity
{
    private static final String TAG = RecordActivity.class.getSimpleName();
    Button btn;
    TextView view1;
    TextView view2;
    TextView view3;
    DatabaseOpenHelper databaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Bundle bundle = getIntent().getExtras();
        databaseOpenHelper = new DatabaseOpenHelper(this);

        view1 = findViewById(R.id.date);
        view2 = findViewById(R.id.water);
        view3 = findViewById(R.id.quantity);
        btn = findViewById(R.id.delete);

        assert bundle != null;
        final String drink_id = bundle.getString("drink_id");
        final String date_id = bundle.getString("date_id");
        final String quantity = bundle.getString("quantity");

        String date_record = "Date: " + date_id;
        String drink_record = "Drink: " + drink_id;
        String quantity_record = "Quantity: " + quantity + " ml";

        view1.setText(date_record);
        view2.setText(drink_record);
        view3.setText(quantity_record);
        btn.setText(R.string.delete);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataManager.getInstance().deleteRecord(databaseOpenHelper, date_id, drink_id);
                finish();
            }
        });
    }
}

