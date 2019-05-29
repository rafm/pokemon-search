// TODO Use fetch polyfill?

const pokemonSearchButton = document.querySelector(".pokemon-search-button");
const form = document.querySelector("#pokemon-search-form");
const pokemonInfo = document.querySelector(".pokemon-info");
const cityInfo = document.querySelector(".city-info");
pokemonSearchButton.addEventListener("click", () => {
    const cityName = form.cityName.value.trim();
    if (cityName) {
        fetch("/api/search?cityName="+cityName).then((response) => {
            if (response.ok) {
                response.json().then((pokemonSearchData) => {
                    pokemonInfo.textContent = buildPokemonInfoText(pokemonSearchData);
                    cityInfo.textContent = buildCityInfoText(cityName, pokemonSearchData);
                });
            }
        });
    }
});

function buildPokemonInfoText(pokemonSearchData) {
    return `Um ${pokemonSearchData.pokemonName} selvagem apareceu!`;
}

function buildCityInfoText(cityName, pokemonSearchData) {
    const rainText = pokemonSearchData.raining ? ' em meio a uma chuva' : '';
    return `Na cidade ${cityName} à temperatura de ${pokemonSearchData.temperature}º${rainText}.`;
}