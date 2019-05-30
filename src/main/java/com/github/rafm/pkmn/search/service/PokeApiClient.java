package com.github.rafm.pkmn.search.service;

import com.github.rafm.pkmn.search.entity.enums.PokemonType;
import com.github.rafm.pkmn.search.service.dto.PokemonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PokeApiClient {

    @Value("${pokeApi.baseUri}")
    private String baseUri;

    @Value("${pokeApi.searchByTypeUri}")
    private String searchByTypeUri;
    
    @Autowired
    @Qualifier("pokemon")
    private WebClient pokemonWebClient;

    /**
     * Retrieves a list of pokemon names from the PokeAPI and validates if the result was built appropriatelly.
     * This method may not retrieve less than 2 pokemon names based on the passed pokemon type, otherwise it
     * will throw an IllegalStateException.
     * 
     * @throws java.lang.IllegalStateException in case of an invalid response list from PokeAPI
     * @param pokemonType type of the pokemons that will be retrieved byt PokeAPI
     * @return the result from PokeAPi containing the list of pokemon names of the passed pokemonType
     */
	public PokemonResponse findAllPokemonNamesByPokemonType(PokemonType pokemonType) {
        PokemonResponse pokemonResponse = pokemonWebClient.get().uri(searchByTypeUri, pokemonType.name().toLowerCase())
            .retrieve().bodyToMono(PokemonResponse.class).block();
        if (pokemonResponse == null || pokemonResponse.getPokemonNames() == null || pokemonResponse.getPokemonNames().size() < 2) {
            throw new IllegalStateException("The PokeAPI is not returning the appropriated number of pokemons based on the pokemon type.");
        }
        return pokemonResponse;
	}

    @Bean
    @Qualifier("pokemon")
    private WebClient pokemonWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(baseUri).build();
    }
}
