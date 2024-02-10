package com.MVP.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;

/**
 * Lombok's dependency helps to create automatically all the variable's getter,setter and also to String methods
 */
@Data
/**
// @JsonIgnoreProperties(ignoreUnknown = true) ignore any unknown properties during JSON serialization and deserialization.
*/
@JsonIgnoreProperties(ignoreUnknown = true)

public class WeatherData {
    /**
     * if the variable name includes such as space or not allowed characters @JsonProperty() this annotation help us to match variables during the deserialization and serialization
     */
    @JsonProperty("city")
    private String city;
    private String condition;
    private String icon;
    private String description;
    private Weather weather;

}
