package com.github.rafm.pkmn.search.service;

import com.github.rafm.pkmn.search.service.dto.WeatherResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class WeatherApiClient {

    @Value("${weatherApi.appId}")
    private String appId;

    @Value("${weatherApi.baseUri}")
    private String baseUri;

    @Value("${weatherApi.searchByCityUri}")
    private String searchByCityUri;
    
    @Autowired
    @Qualifier("weather")
    private WebClient weatherWebClient;

    public Mono<WeatherResponse> searchByCityName(String cityName) {
        return weatherWebClient
            .get().uri(searchByCityUri, cityName, appId)
            .retrieve().bodyToMono(WeatherResponse.class);
    }

    @Bean
    @Qualifier("weather")
    private WebClient weatherWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(baseUri).build();
    }
}
