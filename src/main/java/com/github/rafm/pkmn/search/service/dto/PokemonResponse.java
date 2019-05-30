package com.github.rafm.pkmn.search.service.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSetter;

// TODO Improve: remove this DTO, as we can change it only to a List<String> with the pokemon names instead of using this DTO
public class PokemonResponse {

    private List<String> pokemonNames;

    public List<String> getPokemonNames() {
        return pokemonNames;
    }

    @JsonSetter
    public void setPokemon(List<Map<String, Object>> pokemon) {
        this.pokemonNames = pokemon != null
                ? pokemon.stream().map(pokemonItem -> {
                    Object pokemonData = pokemonItem.get("pokemon");
                    if (pokemonData instanceof Map<?, ?>) {
                        Map<?, ?> pokemonDataMap = (Map<?, ?>) pokemonData;
                        if (pokemonDataMap.get("name") instanceof String) {
                            return (String) pokemonDataMap.get("name");
                        }
                    }
                    return null;
                }).collect(toList())
                : null;
    }
}