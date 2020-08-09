package com.example.saver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPurchaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText costEditText, descEditText;
    ImageButton bClear;
    Button bAdd;
    String spinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);

        // Setting up Edit Text for cost and desc
        costEditText = findViewById(R.id.cost_amount_field);
        costEditText.requestFocus();

        descEditText = findViewById(R.id.cost_desc_field);

        // Creates buttons and respective onClickListeners
        bClear = findViewById(R.id.button_clear);
        bClear.setOnClickListener(this);

        bAdd = findViewById(R.id.button_add);
        bAdd.setOnClickListener(this);

        // Sets up Spinner with categories
        Spinner spinner = findViewById(R.id.category_spinner_field);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        spinnerText = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_clear:
                Intent clearIntent = new Intent(AddPurchaseActivity.this, MainActivity.class);
                AddPurchaseActivity.this.startActivity(clearIntent);
                break;
            case R.id.button_add:
                // Creates new paidItem object with parameters description, cost and date
                String description = descEditText.getText().toString();
                String cost = costEditText.getText().toString();
                // Formats date
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = sdf.format(date);
                // Paid item List
                PaidItem paidItem = new PaidItem(description, cost, formattedDate, spinnerText);
                ListContainer.paidItems.add(paidItem);
                // Saves data to JSON and SharedPreferences
                saveData();
                // Returns to main activity
                Intent addIntent = new Intent(AddPurchaseActivity.this, MainActivity.class);
                AddPurchaseActivity.this.startActivity(addIntent);
                Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
                break;
        }

    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ListContainer.paidItems);
        editor.putString("item list", json);
        editor.apply();
    }

}