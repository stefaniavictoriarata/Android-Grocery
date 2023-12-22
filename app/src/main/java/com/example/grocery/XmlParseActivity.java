package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.grocery.adapter.GroceryAdapter;
import com.example.grocery.network.HTTPConnection;
import com.example.grocery.parser.XMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XmlParseActivity extends AppCompatActivity {

    public List<Grocery> xmlGroceries;

    ListView xmlListView;

    String urlString = "https://raw.githubusercontent.com/stefaniavictoriarata/Xml-Practice/main/Groceries.xml";

    //GroceryAdapter adapter = new GroceryAdapter(getApplicationContext(), R.layout.lv_groceries_row,xmlGroceries,getLayoutInflater());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);

        xmlGroceries = new ArrayList<Grocery>();
        xmlListView = findViewById(R.id.xml_lv);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = connection.getInputStream();
                    XMLParser parser = new XMLParser();
                    xmlGroceries = parser.parse(inputStream);

                    GroceryAdapter adapter = new GroceryAdapter(getApplicationContext(), R.layout.lv_groceries_row, xmlGroceries, getLayoutInflater());
                    xmlListView.setAdapter(adapter);

                    inputStream.close();
                    connection.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}