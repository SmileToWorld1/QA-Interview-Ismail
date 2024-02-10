package com.MVP.uiPages;

import com.MVP.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    public BasePage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@id='desktop-menu']//input[@placeholder='Weather in your city']")
    private WebElement basePageSearchBox;

    public WebElement getBasePageSearchBox() {return basePageSearchBox;}

}
