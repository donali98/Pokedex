package com.example.poke;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.poke.Pokemon.Pokemon;
import com.example.poke.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected  LinearLayoutManager linearLayoutManager;
    protected  RecyclerView rvPokemon;
    protected  PokemonAdapter pokemonAdapter;
    protected  ArrayList<Pokemon> pokemons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchPokemonTask().execute("1");


    }
    private class FetchPokemonTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                rvPokemon  = findViewById(R.id.rvPoke);
                linearLayoutManager = new LinearLayoutManager( MainActivity.this);
                rvPokemon.setLayoutManager(linearLayoutManager);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                JSONObject jsonObject = null;
                pokemons = new ArrayList<>();
                jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0; i<jsonArray.length();i++){
                    Pokemon pokemon = gson.fromJson(jsonArray.get(i).toString(),Pokemon.class);
                    pokemons.add(pokemon);
                }
                pokemonAdapter = new PokemonAdapter(pokemons) {
                    @Override
                    public void setIntent(String key, String value) {
                        Intent i = new Intent(MainActivity.this,InfoActivity.class);
                        i.putExtra(key,value);
                        startActivity(i);
                    }
                };
                rvPokemon.setAdapter(pokemonAdapter);




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... pokemonNumbers) {

            if (pokemonNumbers.length == 0) {
                return null;
            }


            URL pokeAPI = NetworkUtils.buildUrl();

            try {
                 String result = NetworkUtils.getResponseFromHttpUrl(pokeAPI);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

}
