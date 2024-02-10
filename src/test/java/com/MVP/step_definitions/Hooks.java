package com.MVP.step_definitions;

import com.MVP.utilities.ConfigurationReader;
import com.MVP.utilities.Driver;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static io.restassured.RestAssured.*;


/**
 In this class we will be able to pass pre- & post- conditions to
 each scenario and each step
 */
public class Hooks {
    private final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
    private static boolean isConfigured = false;

    @Before
    public void setup(){
        if (!isConfigured) {
            isConfigured = true;
        }
        baseURI = ConfigurationReader.getProperty("base_url");
        basePath = ConfigurationReader.getProperty("base_path");
        LOGGER.info("baseURI {}, basePath {}",baseURI,basePath);

    }

    @After
    public void teardown(Scenario scenario){
        /**
         *scenario.isFailed --> if scenario fails, this method will return TRUE boolean value
         */
        if (scenario.isFailed() && scenario.getSourceTagNames().contains("@ui")){
            byte [] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());
        }
        Driver.closeDriver();

    }

}


