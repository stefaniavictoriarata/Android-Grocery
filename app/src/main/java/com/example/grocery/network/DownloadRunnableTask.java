package com.example.grocery.network;

import java.io.IOException;

public class DownloadRunnableTask implements Runnable {

    private HTTPConnection httpConnection;

    public DownloadRunnableTask(HTTPConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    @Override
    public void run() {
        try {
            String result = httpConnection.readFromHttp();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
