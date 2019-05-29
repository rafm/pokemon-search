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

    // Defined as static to guarantee only one variable, independent of the bean's scope.
    // But event without the static modifier, this will work properly because this Facade's scope is the default Singleton
    // and Spring will guarantee the existence of only one instance of this class and, consequently, of the variable too.
    private static String lastPokemonName = "";
    private final Random RANDOM = new Random();

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private PokeApiClient pokeApiClient;

    @Autowired
    private PokemonService pokemonService;

    public PokemonSearchResponse searchPokemonByCityName(String cityName) {
        WeatherResponse weatherResponse = weatherApiClient.searchByCityName(cityName);
        PokemonType pokemonType = pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(weatherResponse.isRaining(), weatherResponse.getTemperature());
        PokemonResponse pokemonResponse = pokeApiClient.findAllPokemonNamesByPokemonType(pokemonType);
        // TODO 0 and 1 cases
        // TODO Concurrency
        String pokemonName;
        synchronized (lastPokemonName) {
            do {
                pokemonName = pickRandomPokemonName(pokemonResponse.getPokemonNames());
            } while (pokemonName.equals(lastPokemonName));
            lastPokemonName = pokemonName;
        }
        return new PokemonSearchResponse(weatherResponse.getTemperature(), weatherResponse.isRaining(), pokemonName);
    }

    private String pickRandomPokemonName(List<String> pokemonList) {
        int randomIndex = RANDOM.nextInt(pokemonList.size());
        return pokemonList.get(randomIndex);
    }
}