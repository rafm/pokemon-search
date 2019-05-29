package com.github.rafm.pkmn.search.service;

import com.github.rafm.pkmn.search.entity.enums.PokemonType;

import org.springframework.stereotype.Service;

@Service
public class PokemonService {

    // TODO Refactor
    public PokemonType retrieveInhabitedPokemonTypeBasedOnWeather(boolean raining, int temperature) {
        if (raining) {
            return PokemonType.ELECTRIC;
        }
        if (temperature < 5) {
            return PokemonType.ICE;
        } else if (temperature < 10) {
            return PokemonType.WATER;
        } else if (temperature >= 12 && temperature < 15) {
            return PokemonType.GRASS;
        } else if (temperature >= 15 && temperature < 21) {
            return PokemonType.GROUND;
        } else if (temperature >= 23 && temperature < 27) {
            return PokemonType.BUG;
        } else if (temperature >= 27 && temperature <= 33) {
            return PokemonType.ROCK;
        } else if (temperature > 33) {
            return PokemonType.FIRE;
        }
        return PokemonType.NORMAL;
    }
}