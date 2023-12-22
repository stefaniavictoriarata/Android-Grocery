package com.example.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grocery.Grocery;
import com.example.grocery.R;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends ArrayAdapter<Grocery> {
    private List<Grocery> groceries;
    private LayoutInflater inflater;

    public TextView textName, textPrice, textPerishable;

    private int resource;

    public GroceryAdapter(@NonNull Context context, int resource, @NonNull List<Grocery> groceries, LayoutInflater inflater) {
        super(context, resource, groceries);
        this.resource = resource;
        this.groceries = groceries;
        this.inflater = inflater;
    }

    private void initVisualComponents(View row)
    {
        textName = row.findViewById(R.id.text_name2);
        textPrice = row.findViewById(R.id.text_price2);
        textPerishable = row.findViewById(R.id.text_perishable);
    }

    private void populateVisualComponents(Grocery g)
    {
        if(g != null) {
            textName.setText(g.getName());
            textPrice.setText(Float.toString(g.getPrice()));
            String p = g.isPerishable() ? getContext().getString(R.string.is_p) : getContext().getString(R.string.isnot_p);
            textPerishable.setText(p);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View row = inflater.inflate(this.resource, parent, false);
        initVisualComponents(row);
        Grocery g = groceries.get(position);
        populateVisualComponents(g);
        return row;
    }

}
