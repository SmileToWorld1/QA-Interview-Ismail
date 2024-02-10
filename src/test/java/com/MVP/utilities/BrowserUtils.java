package com.MVP.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.apache.commons.lang.StringUtils.trim;

public class BrowserUtils {

    /**
     * This method accepts a String "expectedTitle" and Asserts if it is true
     */
    public static void verifyTitle(String expectedTitle) {
        String actualTitle = trim(Driver.getDriver().getTitle());
        Assert.assertEquals(actualTitle, trim(expectedTitle), "Title is not matching!");
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param webElement
     */
    public static WebElement waitForVisibility(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Waits for provided element to be clickable
     *
     * @param element
     * @return WebElement
     */
    public static WebElement waitForClickAbility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


}







