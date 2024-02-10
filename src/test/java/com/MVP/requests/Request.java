package com.MVP.requests;

import com.MVP.utilities.MVPBase;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class Request extends MVPBase {

    private static Response response;

    static {
        // "Call the init() method of the MVPBase class to initialize the requestSpec and responseSpec fields."
        MVPBase.init();
    }

    public static Response getWeather() {
            response = given()
                    .spec(requestSpec)
                    .when()
                    .get(baseURI + basePath)
                    .then()
                    .extract().response();
        return response;
    }

    public static Response postWeather() {
        response = given()
                .spec(requestSpec)
                .when()
                .post(baseURI + basePath)
                .then()
                .extract().response();
        return response;
    }
    public static Response putWeather() {
        response = given()
                .spec(requestSpec)
                .when()
                .put(baseURI + basePath)
                .then()
                .extract().response();
        return response;
    }

    public static Response patchWeather() {
        response = given()
                .spec(requestSpec)
                .when()
                .patch(baseURI + basePath)
                .then()
                .extract().response();
        return response;
    }

    public static Response putTemp(Object tempInFahrenheit) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tempInFahrenheit", tempInFahrenheit);

            response = given()
                    .spec(requestSpec)
                    .and()
                    .body(requestBody)
                    .when()
                    .put(baseURI + basePath + "/temp")
                    .then()
                    .extract().response();
        return response;
    }

    public static Response putCondition(Object conditionId) {
            response = given()
                    .spec(requestSpec)
                    .and()
                    .body("{\"condition\": "+conditionId+" }")
                    .when()
                    .put(baseURI + basePath + "/condition")
                    .then()
                    .extract().response();
        return response;
    }


}
