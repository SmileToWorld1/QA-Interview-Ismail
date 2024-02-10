package com.MVP.uiPages;

import com.MVP.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class FindOpenWeatherMapPage extends BasePage{

    public FindOpenWeatherMapPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//td/b/a")
    private List<WebElement> listOfCities;

   // public List<WebElement> getListOfCities()  { return listOfCities; }

    public WebElement findCityByText(String text) {
        Optional<WebElement> cityElement = listOfCities.stream()
                .filter(e -> e.getText().equals(text))
                .findFirst();

        return cityElement.orElseThrow(() -> new NoSuchElementException("City not found: " + text));
    }


}
