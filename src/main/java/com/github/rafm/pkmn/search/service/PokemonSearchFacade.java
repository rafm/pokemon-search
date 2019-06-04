package com.github.rafm.pkmn.search.service;

import java.util.List;
import java.util.Random;

import com.github.rafm.pkmn.search.entity.enums.PokemonType;
import com.github.rafm.pkmn.search.service.dto.PokemonResponse;
import com.github.rafm.pkmn.search.service.dto.PokemonSearchResponse;
import com.github.rafm.pkmn.search.service.dto.WeatherResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonSearchFacade {

    /**
     * Defined as static to guarantee only one variable, independent of the bean's scope.
     * But event without the static modifier, this will work properly because this Facade's scope is the default Singleton
     * and Spring will guarantee the existence of only one instance of this class and, consequently, of the variable too.
     */
    private static String lastPokemonName = "";
    private final Random RANDOM = new Random();

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private PokeApiClient pokeApiClient;

    @Autowired
    private PokemonService pokemonService;

    /**
     * Method to search for a random non-repeated pokemon based on a city.
     * 
     * @param cityName to search for a pokemon
     * @return the pokemon found on the passed city
     */
    public PokemonSearchResponse searchPokemonByCityName(String cityName) {
        WeatherResponse weatherResponse = weatherApiClient.searchByCityName(cityName);
        PokemonType pokemonType = pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(weatherResponse.isRaining(), weatherResponse.getTemperature());
        PokemonResponse pokemonResponse = pokeApiClient.findAllPokemonNamesByPokemonType(pokemonType);
        String pokemonName = pickRandomPokemonName(pokemonResponse.getPokemonNames());
        return new PokemonSearchResponse(weatherResponse.getTemperature(), weatherResponse.isRaining(), pokemonName);
    }

    /**
     * Synchronized last pokemon name to deal with concurrency when reading/writing this variable, as there will be only one
     * accessed by multiple clients/requests at the same time. Just like a read commited isolation level in a DB transaction,
     * as i've locked this variable before reading and writing, no client will read a dirty value of this variable.
     * 
     * TODO: Improve concurrency solution performance (because of the random logic being executed while the resource is locked).
     * 
     * @param pokemonNames list with the pokemon names to pick a random one
     * @return a random pokemon name from the passed pokemon names list
     */
    private synchronized String pickRandomPokemonName(List<String> pokemonNames) {
        String pokemonName;
        do {
            int randomIndex = RANDOM.nextInt(pokemonNames.size());
            pokemonName = pokemonNames.get(randomIndex);
        } while (pokemonName.equals(lastPokemonName));

        return lastPokemonName = pokemonName;
    }
}