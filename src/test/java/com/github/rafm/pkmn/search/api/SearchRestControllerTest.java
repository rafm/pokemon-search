package com.github.rafm.pkmn.search.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import com.github.rafm.pkmn.search.service.PokemonSearchFacade;
import com.github.rafm.pkmn.search.service.dto.PokemonSearchResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RunWith(MockitoJUnitRunner.class)
public class SearchRestControllerTest {

    @Mock
    private PokemonSearchResponse mockedResponse;

    @Mock
    private WebClientResponseException mockedWebClientResponseException;

    @Mock
    private PokemonSearchFacade pokemonSearchFacade;

    @InjectMocks
    private SearchRestController searchRestController;

    @Test
    public void testSearchPokemon() {
        when(pokemonSearchFacade.searchPokemonByCityName("Campinas")).thenReturn(mockedResponse);

        ResponseEntity<PokemonSearchResponse> response = searchRestController.searchPokemon("Campinas");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(mockedResponse, response.getBody());
    }

    @Test
    public void testSearchPokemonWithNullCityName() {
        ResponseEntity<PokemonSearchResponse> response = searchRestController.searchPokemon(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testSearchPokemonWithEmptyCityName() {
        ResponseEntity<PokemonSearchResponse> response = searchRestController.searchPokemon("    ");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testHandleException() {
        when(mockedWebClientResponseException.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        when(mockedWebClientResponseException.getResponseBodyAsString()).thenReturn("BODY");

        ResponseEntity<String> handledException = searchRestController.handleException(mockedWebClientResponseException);

        assertEquals(HttpStatus.I_AM_A_TEAPOT, handledException.getStatusCode());
        assertEquals("BODY", handledException.getBody());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleExceptionWithNullHttpStatusCode() {
        searchRestController.handleException(mockedWebClientResponseException);
    }
}