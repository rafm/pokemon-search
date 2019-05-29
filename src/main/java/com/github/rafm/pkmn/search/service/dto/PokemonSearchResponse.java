package com.github.rafm.pkmn.search.service.dto;

public class PokemonSearchResponse {

    private int temperature;
    private boolean raining;
    private String pokemonName;

    public PokemonSearchResponse(int temperature, boolean raining, String pokemonName) {
        this.temperature = temperature;
        this.raining = raining;
        this.pokemonName = pokemonName;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }
}