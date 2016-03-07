package com.android.tusharg.sunshine;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tushar on 06/03/16.
 */
public class HttpManager {
    public static String getRequest(String uri) {

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.connect();

            reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        } catch (java.io.IOException e) {
            Log.e("HttpManagergetRequest", "IOException ", e);
            return null;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                Log.e("HttpManagergetRequest", "IOException ", e);
            }
        }
    }
}
