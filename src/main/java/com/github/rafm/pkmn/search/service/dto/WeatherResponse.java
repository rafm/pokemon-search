package com.github.rafm.pkmn.search.service.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class WeatherResponse {

    private String weather;
    private Integer temperature;
    
    public String getWeather() {
        return weather;
    }

    @JsonIgnore
    public boolean isRaining() {
        return "Rain".equals(weather);
    }

    public Integer getTemperature() {
        return temperature;
    }

    @JsonSetter
    private void setWeather(List<Map<String, String>> weather) {
        this.weather = weather != null && !weather.isEmpty() ? weather.get(0).get("main") : null;
    }

    @JsonSetter
    private void setMain(Map<String, Integer> main) {
        this.temperature = main != null ? main.get("temp") : null;
    }
}
