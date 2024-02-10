package com.MVP.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;



/**
 * Lombok's dependency helps to create automatically all the variable's getter,setter and also to String methods
 */
@Data
/**
 // @JsonIgnoreProperties(ignoreUnknown = true) ignore any unknown properties during JSON serialization and deserialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Weather {

    private int tempInFahrenheit;
    private int tempInCelsius;

}
