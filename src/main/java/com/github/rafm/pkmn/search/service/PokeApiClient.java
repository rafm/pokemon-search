package com.github.rafm.pkmn.search.service;

import com.github.rafm.pkmn.search.entity.enums.PokemonType;
import com.github.rafm.pkmn.search.service.dto.PokemonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class PokeApiClient {

    private static final String BASE_URI = "https://pokeapi.co/api/v2";
    private static final String SEARCH_BY_TYPE_URI = "/type/{pokemonType}";
    
    @Autowired
    @Qualifier("pokemon")
    private WebClient pokemonWebClient;

	public Mono<PokemonResponse> findAllPokemonNamesByPokemonType(PokemonType pokemonType) {
        return pokemonWebClient
            .get().uri(SEARCH_BY_TYPE_URI, pokemonType.name().toLowerCase())
            .retrieve().bodyToMono(PokemonResponse.class);
	}

    @Bean
    @Qualifier("pokemon")
    private WebClient pokemonWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(BASE_URI).build();
    }
}
