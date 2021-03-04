package com.company.komzak.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.company.komzak.models.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HttpService {

    private String BASE_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    private String API_KEY = "dict.1.1.20200306T120634Z.d00d75bcc7d48132.b4d5929e16f0731a22a9c8d1de6d94025ba28b8f";

    private Gson gson = new Gson();

    public List<String> getLanguagesText() {
        StringBuilder stringBuilder = new StringBuilder();
        String langUrl = BASE_URL + "getLangs?key=" + API_KEY;
        try {
            URL url = new URL(langUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                stringBuilder.append(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(stringBuilder.toString(), type);
    }

    public String getTranslateText(String lang, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        String langUrl = BASE_URL + "lookup?key=" + API_KEY + "&lang=" + lang + "&text=" + text;
        try {
            URL url = new URL(langUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                stringBuilder.append(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Translator translator = gson.fromJson(stringBuilder.toString(), Translator.class);
        return translator.getDef().get(0).getTr().get(0).getText();
    }
}
