package com.MVP.uiPages;

import com.MVP.utilities.Driver;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage{
    public MainPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }


}
