package com.MVP.step_definitions;

import com.MVP.pojo.WeatherData;
import com.MVP.requests.Request;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class ApiWeatherDataStepDefinitions {
    private final Logger LOGGER = LoggerFactory.getLogger(ApiWeatherDataStepDefinitions.class);
    private Response weatherApiResponse;
    private WeatherData responseWeatherData;

    private List<Response> weatherApiResponses = new ArrayList<>();

    @When("I request the current weather for the city {string}")
    public void i_request_the_current_weather_for_the_city(String city) {
        weatherApiResponse = Request.getWeather();
        // Map the JSON response to a WeatherData object
        responseWeatherData = weatherApiResponse.as(WeatherData.class);
        LOGGER.info("responseWeatherData is {}", responseWeatherData);
        assertEquals(responseWeatherData.getCity(), city, "Actual city name is not equal with expected city name");
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        if (weatherApiResponse.getStatusCode() != expectedStatus)
            LOGGER.error("Expected status is {}, but weatherApiResponse getStatusCode is {}", expectedStatus, weatherApiResponse.getStatusCode());
        assertEquals(weatherApiResponse.getStatusCode(), expectedStatus, "The response status is not equal with expected status code");
    }

    @And("the response time should be under {int}")
    public void the_response_time_should_be_under(int expectedResponseTime) {
        if (weatherApiResponse.time() > expectedResponseTime)
            LOGGER.error("Expected response time is {}, but the actual response time is {}",expectedResponseTime, weatherApiResponse.time());
        assertTrue(weatherApiResponse.time() <= expectedResponseTime, "The response time is bigger than expected response time");
    }

    @And("the response body fields should not be null")
    public void the_response_body_fields_should_not_be_null() {
        assertNotNull(responseWeatherData.getCity(), "City is null");
        assertNotNull(responseWeatherData.getCondition(), "Condition is null");
        assertNotNull(responseWeatherData.getIcon(), "Icon is null");
        assertNotNull(responseWeatherData.getDescription(), "Description is null");
        assertNotNull(String.valueOf(responseWeatherData.getWeather()), "Weather is null");
    }

    @When("I submit a invalid {string} method to retrieval weather data")
    public void i_submit_a_invalid_methods_to_retrieval_weather_data(String invalidMethod) {
        switch (invalidMethod.toUpperCase()) {
            case "POST":
                weatherApiResponse = Request.postWeather();
                LOGGER.info("weatherApiResponse is {}", weatherApiResponse.asString());
                break;
            case "PUT":
                weatherApiResponse = Request.putWeather();
                LOGGER.info("weatherApiResponse is {}", weatherApiResponse.asString());
                break;
            case "PATCH":
                weatherApiResponse = Request.patchWeather();
                LOGGER.info("weatherApiResponse is {}", weatherApiResponse.asString());
                break;
        }

    }

    @Then("the response body should contain the error message {string}")
    public void the_response_body_should_contain_the_error_message(String expectedErrorMessage) {
        String responseBody = weatherApiResponse.getBody().asString();
        LOGGER.info("The response body is {}",responseBody);
        String actualErrorMessage = JsonPath.from(responseBody).getString("error");
        if (!actualErrorMessage.equals(expectedErrorMessage))
            LOGGER.error("Expected error message is {}, but actual error message is {}",expectedErrorMessage, actualErrorMessage);
        assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message in the response body does not match the expected error message");
    }


    @When("I send the condition ID {int} with putCondition request")
    public void i_send_the_condition_id_with_put_condition_request(Integer conditionId) {
        weatherApiResponse = Request.putCondition(conditionId);
    }

    @Then("the response should have the {string} field with value {string}")
    public void the_response_should_have_the_field_with_value(String fieldName,String expectedFieldValue) {
        responseWeatherData = weatherApiResponse.as(WeatherData.class);
        try {
            Field field;
            Object value;
            if (fieldName.equals("tempInFahrenheit") || fieldName.equals("tempInCelsius")){
                LOGGER.info("responseWeatherData.getWeather is {}", responseWeatherData.getWeather());
                field =responseWeatherData.getWeather().getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(responseWeatherData.getWeather());
            }else {
                LOGGER.info("responseWeatherData is {}", responseWeatherData);
                field = responseWeatherData.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(responseWeatherData);
            }

            String actualValue = String.valueOf(value);

            assertTrue(actualValue.contains(expectedFieldValue),
                    "Expected value part [" + expectedFieldValue + "] is not contained in actual value [" + actualValue + "]");
        } catch (NoSuchFieldException e) {
            LOGGER.error("Field '{}' does not exist on {}", fieldName, responseWeatherData.getClass().getSimpleName(), e);
            throw new RuntimeException("Field does not exist: " + fieldName);
        } catch (IllegalAccessException e) {
            LOGGER.error("Illegal access to field '{}'", fieldName, e);
            throw new RuntimeException("Cannot access the field: " + fieldName);
        }
    }

    @When("I send the Fahrenheit temperature {int} with putTemp request")
    public void i_send_the_fahrenheit_temperature_with_put_temp_request(Integer tempInFahrenheit) {
        weatherApiResponse = Request.putTemp(tempInFahrenheit);
    }


    @And("{string} field value should have zero digits")
    public void field_value_should_have_zero_digits(String fieldName) {
        try {
            Field field;
            Object value;

            if (fieldName.equals("tempInFahrenheit") || fieldName.equals("tempInCelsius")){
                LOGGER.info("responseWeatherData.getWeather is {}", responseWeatherData.getWeather());
                field =responseWeatherData.getWeather().getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(responseWeatherData.getWeather());
            }else {
                LOGGER.info("responseWeatherData is {}", responseWeatherData);
                field = responseWeatherData.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(responseWeatherData);
            }

            double actualValue = Double.valueOf(value.toString());
            boolean hasNoFractionalPart = actualValue == Math.floor(actualValue);

            LOGGER.info("Actual value: " + actualValue);
            LOGGER.info("Has no fractional part: " + hasNoFractionalPart);

            // Assert that the value should not have a fractional part
            assertTrue( hasNoFractionalPart,"The value should not have a fractional part.");
        } catch (NoSuchFieldException e) {
            LOGGER.error("Field '{}' does not exist on {}", fieldName, responseWeatherData.getClass().getSimpleName(), e);
            throw new RuntimeException("Field does not exist: " + fieldName);
        } catch (IllegalAccessException e) {
            LOGGER.error("Illegal access to field '{}'", fieldName, e);
            throw new RuntimeException("Cannot access the field: " + fieldName);
        }
    }





}
