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

    private static final String BASE_URI = "https://api.openweathermap.org/data/2.5";
    private static final String SEARCH_BY_CITY_URI = "/weather?q={city}&units=metric&appid={appid}";

    @Value("${weatherApi.appId}")
    private String appId;
    
    @Autowired
    @Qualifier("weather")
    private WebClient weatherWebClient;

    public Mono<WeatherResponse> searchByCityName(String cityName) {
        return weatherWebClient
            .get().uri(SEARCH_BY_CITY_URI, cityName, appId)
            .retrieve().bodyToMono(WeatherResponse.class);
    }

    @Bean
    @Qualifier("weather")
    private WebClient weatherWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(BASE_URI).build();
    }
}
