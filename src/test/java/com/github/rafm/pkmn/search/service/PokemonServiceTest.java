package com.github.rafm.pkmn.search.service;

import static com.github.rafm.pkmn.search.entity.enums.PokemonType.BUG;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.ELECTRIC;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.FIRE;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.GRASS;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.GROUND;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.ICE;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.NORMAL;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.ROCK;
import static com.github.rafm.pkmn.search.entity.enums.PokemonType.WATER;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PokemonServiceTest {

    private PokemonService pokemonService = new PokemonService();

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherRainingAlwaysReturnsElectricType() {
        assertEquals(ELECTRIC, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(true, 0)); // lowest value
        assertEquals(ELECTRIC, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(true, 11)); // Normal pokemon
                                                                                                     // type value
        assertEquals(ELECTRIC, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(true, 20)); // medium value
        assertEquals(ELECTRIC, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(true, 40)); // highest value
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherNormalType() { // all possible values
        assertEquals(NORMAL, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 10));
        assertEquals(NORMAL, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 11));
        assertEquals(NORMAL, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 21));
        assertEquals(NORMAL, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 22));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherIceType() {
        assertEquals(ICE, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, -20));
        assertEquals(ICE, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 4));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherWaterType() {
        assertEquals(WATER, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 5));
        assertEquals(WATER, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 9));
        assertEquals(WATER, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 7));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherGrassType() {
        assertEquals(GRASS, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 12));
        assertEquals(GRASS, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 13));
        assertEquals(GRASS, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 14));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherGroundType() {
        assertEquals(GROUND, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 15));
        assertEquals(GROUND, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 17));
        assertEquals(GROUND, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 20));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherBugType() {
        assertEquals(BUG, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 23));
        assertEquals(BUG, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 25));
        assertEquals(BUG, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 26));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherRockType() {
        assertEquals(ROCK, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 27));
        assertEquals(ROCK, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 30));
        assertEquals(ROCK, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 33));
    }

    @Test
    public void testRetrieveInhabitedPokemonTypeBasedOnWeatherFireType() {
        assertEquals(FIRE, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 34));
        assertEquals(FIRE, pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(false, 50));
    }
}
