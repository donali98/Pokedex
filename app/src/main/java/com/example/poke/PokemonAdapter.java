package com.example.poke;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poke.Pokemon.Pokemon;

import java.util.ArrayList;

public abstract class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    public TextView textView;
    public CardView card;
    public ArrayList<Pokemon> pokemons;

    public PokemonAdapter(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PokemonViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder pokemonViewHolder, int i) {
        final Pokemon pokemon = pokemons.get(i);
        textView.setText(pokemon.getName());
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntent("info",pokemon.getUrl());
            }
        });
    }

    public abstract void setIntent(String key, String value);

    @Override
    public int getItemCount() {
        return pokemons.size();
    }
    class PokemonViewHolder extends RecyclerView.ViewHolder{

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.lblText);
            card = itemView.findViewById(R.id.card);

        }
    }
}
