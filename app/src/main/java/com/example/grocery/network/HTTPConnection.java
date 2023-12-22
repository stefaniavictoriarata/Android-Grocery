package com.example.grocery.network;

import com.example.grocery.Grocery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HTTPConnection {
    private String urlString;
    private List<Grocery> groceryList;
    private HttpURLConnection httpConnection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader reader;

    public HTTPConnection(String urlString)
    {
        this.urlString = urlString;
    }
    public void openConnection() throws IOException {
        URL url = new URL(urlString);
        httpConnection = (HttpURLConnection) url.openConnection();
        inputStream = httpConnection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);
    }

    public void closeConnection() throws IOException {
        reader.close();
        inputStreamReader.close();
        inputStream.close();
        httpConnection.disconnect();
    }

    public String readFromHttp() throws IOException {
        StringBuilder result = new StringBuilder();
        openConnection();
        String line;
        while((line=reader.readLine())!=null)
        {
            result.append(line);
        }
        closeConnection();
        return result.toString();
    }
}
