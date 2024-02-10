package com.MVP.uiPages;


import com.MVP.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WeatherForecastPage extends BasePage {
    public WeatherForecastPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@class='orange-text']")
    private WebElement selectedCityCurrentDateAndTime;

    public WebElement getSelectedCityCurrentDateAndTime() {
        return selectedCityCurrentDateAndTime;
    }
}
