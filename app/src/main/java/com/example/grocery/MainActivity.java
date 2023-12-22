package com.example.grocery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.grocery.adapter.GroceryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnJson, btnXml;
    ListView lvGroceries;
    public ArrayList<Grocery> groceries;

    private ActivityResultLauncher<Intent> addLauncher;


    public static final int REQUEST_CODE = 200;
    public static String KEY_JSON_GROCERY = "Grocery";
    public static String KEY_JSON_TITLE="title";
    public static String KEY_JSON_PERISHABLE="perishable";
    public static String KEY_JSON_PRICE="price";

    private ActivityResultCallback<ActivityResult> getCallback()
    {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK && result.getData()!=null)
                {
                    Grocery g = (Grocery)result.getData().getSerializableExtra(AddGrocery.KEY_GROCERY);
                    groceries.add(g);
                    GroceryAdapter adapter = (GroceryAdapter)lvGroceries.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }
    private ActivityResultLauncher<Intent> registerAddLauncher()
    {
        ActivityResultCallback<ActivityResult> callback = getCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    public void initComponents()
    {
        btnAdd = findViewById(R.id.btn_add);
        btnJson = findViewById(R.id.btn_json);
        btnXml = findViewById(R.id.btn_xml);
        lvGroceries = findViewById(R.id.lv_groceries);
        groceries = new ArrayList<Grocery>();

        GroceryAdapter adapter = new GroceryAdapter(getApplicationContext(), R.layout.lv_groceries_row,
                groceries, getLayoutInflater());
        lvGroceries.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addLauncher = registerAddLauncher();


        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), JsonParseActivity.class);
                startActivity(intent1);
                //addLauncher.launch(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddGrocery.class);
                addLauncher.launch(intent);
            }
        });

        btnXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentXml = new Intent(getApplicationContext(), XmlParseActivity.class);
                startActivity(intentXml);
            }
        });
        JsonParser parser = new JsonParser();
        Thread parseJson = new Thread(parser);
        parseJson.start();

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
//        {
//            Grocery g = (Grocery)data.getParcelableExtra(AddGrocery.KEY_GROCERY);
//            if(g != null)
//            {
//                groceries.add(g);
//                ArrayAdapter adapter = (ArrayAdapter) lvGroceries.getAdapter();
//                adapter.notifyDataSetChanged();
//            }
//        }
//    }

    public class JsonParser implements Runnable
    {

        @Override
        public void run() {
            try {
                URL url = new URL("https://www.jsonkeeper.com/b/DF5S");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line = reader.readLine();
                reader.close();
                inputStreamReader.close();
                inputStream.close();
                connection.disconnect();

                getGroceryFromJson(line);

                Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        GroceryAdapter adapter = (GroceryAdapter) lvGroceries.getAdapter();
                        adapter.notifyDataSetChanged();
                    }
                });


            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getGroceryFromJson(String json)
    {
        if(json != null)
        {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_JSON_GROCERY);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject groceryJson = jsonArray.getJSONObject(i);
                    String title = groceryJson.getString(KEY_JSON_TITLE);
                    String perishable = groceryJson.getString(KEY_JSON_PERISHABLE);
                    boolean p = perishable.equals("true") ? true : false;
                    float price = (float)(groceryJson.getDouble(KEY_JSON_PRICE));
                    Grocery g = new Grocery(title, p, price);
                    groceries.add(g);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            Log.e("ParseJSON", "JSON Object is null");
        }
    }
}
