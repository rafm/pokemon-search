package com.github.rafm.pkmn.search.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.github.rafm.pkmn.search.entity.enums.PokemonType;
import com.github.rafm.pkmn.search.service.dto.PokemonResponse;
import com.github.rafm.pkmn.search.service.dto.PokemonSearchResponse;
import com.github.rafm.pkmn.search.service.dto.WeatherResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PokemonSearchFacadeTest {

    private static final String CITY_NAME = "Campinas";
    private static final int TEMPERATURE = 10;
    private static final boolean RAINING = false;
    private static final List<String> NORMAL_POKEMON_NAMES = asList("rattata", "meowth");

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private WeatherResponse weatherResponse;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private PokeApiClient pokeApiClient;

    @Mock
    private PokemonResponse pokemonResponse;

    @InjectMocks
    private PokemonSearchFacade pokemonSearchFacade;

    @Before
    public void setUp() {
        when(weatherApiClient.searchByCityName(CITY_NAME)).thenReturn(weatherResponse);
        when(weatherResponse.isRaining()).thenReturn(RAINING);
        when(weatherResponse.getTemperature()).thenReturn(TEMPERATURE);
        when(pokemonService.retrieveInhabitedPokemonTypeBasedOnWeather(RAINING, TEMPERATURE)).thenReturn(PokemonType.NORMAL);
        when(pokeApiClient.findAllPokemonNamesByPokemonType(PokemonType.NORMAL)).thenReturn(pokemonResponse);
        when(pokemonResponse.getPokemonNames()).thenReturn(NORMAL_POKEMON_NAMES);
    }

    @Test
    public void testSearchPokemonByCityName() {
        PokemonSearchResponse pokemonSearchResponse = pokemonSearchFacade.searchPokemonByCityName(CITY_NAME);

        assertNotNull(pokemonSearchResponse);
        assertTrue(NORMAL_POKEMON_NAMES.contains(pokemonSearchResponse.getPokemonName()));
        assertEquals(TEMPERATURE, pokemonSearchResponse.getTemperature());
        assertEquals(RAINING, pokemonSearchResponse.isRaining());
        verify(weatherApiClient).searchByCityName(CITY_NAME);
        verify(pokemonService).retrieveInhabitedPokemonTypeBasedOnWeather(RAINING, TEMPERATURE);
        verify(pokeApiClient).findAllPokemonNamesByPokemonType(PokemonType.NORMAL);

    }

    @Test
    public void testSearchPokemonByCityNameNonRepeatedPokemonReturned() {
        String lastPokemonNameReturned = "";
        for (int i = 0; i < 50; i++) {
            PokemonSearchResponse pokemonSearchResponse = pokemonSearchFacade.searchPokemonByCityName(CITY_NAME);
            assertNotEquals(lastPokemonNameReturned, pokemonSearchResponse.getPokemonName());
            lastPokemonNameReturned = pokemonSearchResponse.getPokemonName();
        }
    }
}
