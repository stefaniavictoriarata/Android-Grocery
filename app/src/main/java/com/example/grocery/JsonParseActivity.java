package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.grocery.adapter.GroceryAdapter;
import com.example.grocery.network.DownloadCallableTask;
import com.example.grocery.network.HTTPConnection;
import com.example.grocery.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JsonParseActivity extends AppCompatActivity {

    public ListView lvGroceriesJson;
    public List<Grocery> list = new ArrayList<>();

    public static final String url = "https://www.jsonkeeper.com/b/DF5S";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parse);

        lvGroceriesJson = findViewById(R.id.j_lv_groceries);

        HTTPConnection connection = new HTTPConnection(url);
        DownloadCallableTask thread = new DownloadCallableTask(connection);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> futureString = executorService.submit(thread);

        try {
            String result = futureString.get();
           JSONParser parser = new JSONParser();
           list = parser.getGroceryFromJSON(result);
            GroceryAdapter jsonAdapter = new GroceryAdapter(getApplicationContext(), R.layout.lv_groceries_row, list, getLayoutInflater());
            lvGroceriesJson.setAdapter(jsonAdapter);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}