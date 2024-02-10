package com.MVP.utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.*;

public class MVPBase {
    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;
@BeforeMethod
    public static void init() {

    // common repeating request actions could pass here contentType-acceptType-authorization ...
        requestSpec = given()
                .contentType(ContentType.JSON)
                .and()
                .accept(ContentType.JSON)
                .log().all();

    // common repeating response actions could pass here a couple of validations ...
        responseSpec = expect()
                .contentType(ContentType.JSON)
                .logDetail(LogDetail.ALL);

    }



    @AfterMethod
    public static void close(){
        //reset the info we set above
        reset();

    }

}
