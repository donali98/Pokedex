package com.example.poke.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    public static final String POKEMON_API_BASE_URL = "https://pokeapi.co/api/v2/";
    public static final String POKEMON_INFO = "pokemon";
    public static final String POKEMON_RANGE_MIN = "limit=0";
    public static final String POKEMON_RANGE_MAX = "offset=1";

    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static URL buildUrl() {
        URL url = null;

        try {
            URI uri = new URI(
                    "https",
                    "pokeapi.co",
                    "/api/v2/pokemon",
                    POKEMON_RANGE_MIN,
                    POKEMON_RANGE_MAX
            );

            url = new URL(uri.toASCIIString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
