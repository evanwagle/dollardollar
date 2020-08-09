package com.example.saver;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText costEditText, descEditText;
    ImageButton bClear;
    Button bSave;
    String spinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        // Setting up Edit Text for cost and desc from current item
        costEditText = findViewById(R.id.cost_amount_field);
        costEditText.setText(ListContainer.paidItems.get(ListContainer.position).getCost());
        costEditText.requestFocus();

        descEditText = findViewById(R.id.cost_desc_field);
        descEditText.setText(ListContainer.paidItems.get(ListContainer.position).getDescription());

        // Creates buttons and respective onClickListeners
        bClear = findViewById(R.id.button_clear);
        bClear.setOnClickListener(this);

        bSave = findViewById(R.id.button_save);
        bSave.setOnClickListener(this);

        // TODO ADD DELETE BUTTON

        // Sets up Spinner with categories
        Spinner spinner = findViewById(R.id.category_spinner_field);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Gets spinner position based on spinner category
        String category = ListContainer.paidItems.get(ListContainer.position).getCategory();
        if (category.equals(getString(R.string.category_item_1)))
            spinner.setSelection(0);
        else if (category.equals(getString(R.string.category_item_2)))
            spinner.setSelection(1);
        else if (category.equals(getString(R.string.category_item_3)))
            spinner.setSelection(2);
        else if (category.equals(getString(R.string.category_item_4)))
            spinner.setSelection(3);
        else if (category.equals(getString(R.string.category_item_5)))
            spinner.setSelection(4);
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
                Intent clearIntent = new Intent(EditActivity.this, MainActivity.class);
                EditActivity.this.startActivity(clearIntent);
                break;
            case R.id.button_save:
                // Creates new paidItem object with parameters description, cost and date
                String description = descEditText.getText().toString();
                String cost = costEditText.getText().toString();
                // Gets date from old item
                String date = ListContainer.paidItems.get(ListContainer.position).getDate();
                // Creates new paidItem and replaces item
                PaidItem paidItem = new PaidItem(description, cost, date, spinnerText);
                ListContainer.paidItems.set(ListContainer.position, paidItem);
                // Saves data to JSON and SharedPreferences
                saveData();
                // Returns to main activity
                Intent saveIntent = new Intent(EditActivity.this, MainActivity.class);
                EditActivity.this.startActivity(saveIntent);
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
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