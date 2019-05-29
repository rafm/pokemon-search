package com.github.rafm.pkmn.search.service;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.rafm.pkmn.search.entity.enums.PokemonType;
import com.github.rafm.pkmn.search.service.dto.PokemonResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class PokeApiClientTest {

    @Mock
    private WebClient pokemonWebClient;

    // TODO Fix generics issue
    @Mock
    private RequestHeadersUriSpec mockedRequestHeadersUriSpec;

    @Mock
    private Mono<PokemonResponse> mockedPokemonResponse;

    @InjectMocks
    private PokeApiClient pokeApiClient;

    @Before
    public void setUp() {
        ResponseSpec mockedResponseSpec = mock(ResponseSpec.class);
        when(pokemonWebClient.get()).thenReturn(mockedRequestHeadersUriSpec);
        when(mockedRequestHeadersUriSpec.uri(isNull(), anyString())).thenReturn(mockedRequestHeadersUriSpec);
        when(mockedRequestHeadersUriSpec.retrieve()).thenReturn(mockedResponseSpec);
        when(mockedResponseSpec.bodyToMono(PokemonResponse.class)).thenReturn(mockedPokemonResponse);
    }

    @Test
    public void testFindAllPokemonNamesByPokemonType() {
        Mono<PokemonResponse> response = pokeApiClient.findAllPokemonNamesByPokemonType(PokemonType.ELECTRIC);

        assertSame(mockedPokemonResponse, response);
        verify(mockedRequestHeadersUriSpec).uri(null, PokemonType.ELECTRIC.name().toLowerCase());
    }

    @Test(expected = NullPointerException.class)
    public void testFindAllPokemonNamesByPokemonTypeWithNullPokemonType() {
        pokeApiClient.findAllPokemonNamesByPokemonType(null);
    }

    @Test(expected = WebClientResponseException.class)
    public void testFindAllPokemonNamesByPokemonTypeNotHandlingWebClientResponseException() {
        when(mockedRequestHeadersUriSpec.retrieve()).thenThrow(WebClientResponseException.class);

        pokeApiClient.findAllPokemonNamesByPokemonType(PokemonType.ELECTRIC);
    }
}
