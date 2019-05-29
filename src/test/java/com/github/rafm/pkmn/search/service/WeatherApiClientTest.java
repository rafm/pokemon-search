package com.github.rafm.pkmn.search.service;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.rafm.pkmn.search.service.dto.WeatherResponse;

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
public class WeatherApiClientTest {

    @Mock
    private WebClient weatherWebClient;

    // TODO Fix generics issue
    @Mock
    private RequestHeadersUriSpec mockedRequestHeadersUriSpec;

    @Mock
    private WeatherResponse mockedWeatherResponse;

    @InjectMocks
    private WeatherApiClient weatherApiClient;

    @Before
    public void setUp() {
        ResponseSpec mockedResponseSpec = mock(ResponseSpec.class);
        when(weatherWebClient.get()).thenReturn(mockedRequestHeadersUriSpec);
        when(mockedRequestHeadersUriSpec.uri(isNull(), anyString(), isNull())).thenReturn(mockedRequestHeadersUriSpec);
        when(mockedRequestHeadersUriSpec.retrieve()).thenReturn(mockedResponseSpec);
        when(mockedResponseSpec.bodyToMono(WeatherResponse.class)).thenReturn(Mono.just(mockedWeatherResponse));
    }

    @Test
    public void testSearchByCityName() {
        WeatherResponse response = weatherApiClient.searchByCityName("Campinas");

        assertSame(mockedWeatherResponse, response);
        verify(mockedRequestHeadersUriSpec).uri(null, "Campinas", null);
    }

    @Test(expected = NullPointerException.class)
    public void testSearchByCityNameWithNullCityName() {
        weatherApiClient.searchByCityName(null);
    }

    @Test(expected = WebClientResponseException.class)
    public void testSearchByCityNameNotHandlingWebClientResponseException() {
        when(mockedRequestHeadersUriSpec.retrieve()).thenThrow(WebClientResponseException.class);

        weatherApiClient.searchByCityName("Campinas");
    }
}
