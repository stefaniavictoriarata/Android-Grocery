package com.example.grocery.network;

import java.util.concurrent.Callable;

public class DownloadCallableTask implements Callable<String> {

    private HTTPConnection httpConnection;

    public DownloadCallableTask(HTTPConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    @Override
    public String call() throws Exception {
        String result = httpConnection.readFromHttp();
        return result;
    }
}
