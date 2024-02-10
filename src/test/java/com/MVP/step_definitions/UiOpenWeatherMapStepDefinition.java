package com.MVP.step_definitions;

import com.MVP.uiPages.FindOpenWeatherMapPage;
import com.MVP.uiPages.MainPage;
import com.MVP.uiPages.WeatherForecastPage;
import com.MVP.utilities.BrowserUtils;
import com.MVP.utilities.ConfigurationReader;
import com.MVP.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UiOpenWeatherMapStepDefinition {
    private final Logger LOGGER = LoggerFactory.getLogger(UiOpenWeatherMapStepDefinition.class);
    private MainPage mainPage = new MainPage();
    private FindOpenWeatherMapPage findOpenWeatherMapPage = new FindOpenWeatherMapPage();
    private WeatherForecastPage weatherForecastPage = new WeatherForecastPage();
    private LocalDateTime localDateTime;

    @Given("I am on the main page")
    public void i_am_on_the_main_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("web_url"));
        LOGGER.info("Main page title is: {}",Driver.getDriver().getTitle());
    }

    @Then("I should see the {string} attribute text as a {string}")
    public void i_should_see_the_attribute_text_as_a(String attributeName, String expectedAttributeText) {
        String actualAttributeText = mainPage.getBasePageSearchBox().getAttribute(attributeName);
        LOGGER.info("Actual attribute value is: {} ", actualAttributeText);
        Assert.assertEquals(actualAttributeText, expectedAttributeText, "Expected attribute value is: " + expectedAttributeText + ", but actual attribute value is: " + actualAttributeText);
    }


    @When("I search {string} in the search box")
    public void i_search_in_the_search_box(String text) {
        // Before the search text, be sure that web element is clickable
        BrowserUtils.waitForClickAbility(mainPage.getBasePageSearchBox());
        LOGGER.info("Searched text is: {}",text);
        mainPage.getBasePageSearchBox().sendKeys(text + Keys.ENTER);
    }

    @When("I select {string} from the list")
    public void i_select_from_the_list(String city) {
        WebElement selectedCity = findOpenWeatherMapPage.findCityByText(city);
        LOGGER.info("Selected city is {}",selectedCity.getText());
        BrowserUtils.waitForClickAbility(selectedCity);
        selectedCity.click();
    }

    @Then("the title should be {string}")
    public void the_title_should_be(String expectedTitle) {
        BrowserUtils.waitForVisibility(weatherForecastPage.getSelectedCityCurrentDateAndTime());
        BrowserUtils.verifyTitle(expectedTitle);
    }

    @Given("the selected city time zone is {string}")
    public void theSelectedCityTimeZoneIs(String timeZone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, hh:mma yyyy", Locale.ENGLISH);
        WebElement selectedCityCurrentDateAndTime = weatherForecastPage.getSelectedCityCurrentDateAndTime();
        BrowserUtils.waitForVisibility(selectedCityCurrentDateAndTime);
        String selectedCityDateAndTime= selectedCityCurrentDateAndTime.getText();
        selectedCityDateAndTime = selectedCityDateAndTime.substring(0, selectedCityDateAndTime.length() - 2) + selectedCityDateAndTime.substring(selectedCityDateAndTime.length() - 2).toUpperCase();
        selectedCityDateAndTime = selectedCityDateAndTime + " " + LocalDateTime.now().getYear();
        LOGGER.info("The selected city' date and time is: {}",selectedCityDateAndTime);
        localDateTime = LocalDateTime.parse(selectedCityDateAndTime, formatter);
        LOGGER.info("The given time is: {}",localDateTime);
    }

    @And("the displayed date and time should be the match the current time with a tolerance of {int} minutes")
    public void theDisplayedDateAndTimeShouldBeTheMatchTheCurrentTimeWithAToleranceOfMinutes(int toleranceMinutes) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC+11"));
        LOGGER.info("now: {}",now);
        Duration difference = Duration.between(localDateTime, now).abs();
        LOGGER.info("The difference is: {}",difference);
        Assert.assertTrue(difference.toMinutes() <= toleranceMinutes,"The difference between the given time and the current time is outside the accepted tolerance.");

    }
}
