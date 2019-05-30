const pokemonSearchButton = document.querySelector(".pokemon-search-button");
const form = document.querySelector("#pokemon-search-form");
const pokemonInfo = document.querySelector(".pokemon-info");
const cityInfo = document.querySelector(".city-info");
const pokemonErrorMessage = document.querySelector(".pokemon-error-message");
form.addEventListener("submit", (event) => {
    event.preventDefault();
});
pokemonSearchButton.addEventListener("click", () => {
    const cityName = form.cityName.value.trim();
    if (cityName) {
        fetch("/api/search?cityName="+cityName)
            .then((response) => {
                if (response.ok) {
                    response.json().then((pokemonSearchData) => {
                        pokemonErrorMessage.textContent = "";
                        pokemonInfo.textContent = buildPokemonInfoText(pokemonSearchData);
                        cityInfo.textContent = buildCityInfoText(cityName, pokemonSearchData);
                    });
                } else if (response.status == 404) {
                    pokemonErrorMessage.textContent = "Não conseguimos encontrar esta cidade. = (";
                    pokemonInfo.textContent = "";
                    cityInfo.textContent = "";
                } else if (response.status >= 500) {
                    pokemonErrorMessage.textContent = "Desculpe, mas o serviço está indisponível no momento. = (";
                    pokemonInfo.textContent = "";
                    cityInfo.textContent = "";
                }
            })
            .catch(() => {
                pokemonErrorMessage.textContent = "Ocorreu um problema ao tentar se conectar com o servidor! = O";
                pokemonInfo.textContent = "";
                cityInfo.textContent = "";
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
