package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.grocery.databases.GroceryDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Random;

public class AddGrocery extends AppCompatActivity {

    EditText etName, etPrice;
    RadioGroup rgPerishable;
    FloatingActionButton fabSend;

    Intent intent;

    public static final String KEY_GROCERY = "send";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        initComponent();
        intent = getIntent();
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    Grocery g = createGrocery();
                    if(g != null)
                    {
                        intent.putExtra(KEY_GROCERY, g);
                        insertGroceryInDb(g);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }
    public boolean validate()
    {
        if(etName == null || etPrice == null ||
                etName.getText().toString().trim().isEmpty() ||
                Float.parseFloat(etPrice.getText().toString()) < 0)
        {
            Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public Grocery createGrocery()
    {
            String name = etName.getText().toString();
            float price = Float.parseFloat(etPrice.getText().toString());
            boolean perishable = rgPerishable.getCheckedRadioButtonId() == R.id.rb_yes;
            return new Grocery(name, perishable, price);
    }
    public void initComponent()
    {
        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        fabSend = findViewById(R.id.fab_send);
        rgPerishable = findViewById(R.id.rg_perishable);
    }

    public void insertGroceryInDb(Grocery g)
    {
        GroceryDb groceryDb = GroceryDb.getInstance(getApplicationContext());
        Random random = new SecureRandom();
        Supermarket supermarket = new Supermarket(random.nextInt(), "Lidl", "Bucuresti");
        g.setIdSupermarket(supermarket.getId());

        groceryDb.getSupermarketDao().insert(supermarket);
        groceryDb.getGroceryDao().insert(g);
    }
}