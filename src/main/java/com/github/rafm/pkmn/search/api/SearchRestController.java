package com.github.rafm.pkmn.search.api;

import javax.validation.constraints.NotBlank;

import com.github.rafm.pkmn.search.service.PokemonSearchFacade;
import com.github.rafm.pkmn.search.service.dto.PokemonSearchResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("/api")
public class SearchRestController {

    @Autowired
    private PokemonSearchFacade pokemonSearchFacade;
    
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonSearchResponse> searchPokemon(@RequestParam @NotBlank String cityName) {
        // TODO Fix: Bean Validation's @NotBlank not working
        if (cityName == null || cityName.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pokemonSearchFacade.searchPokemonByCityName(cityName));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleException(WebClientResponseException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsString());
    }
}