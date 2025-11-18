package com.technicaltest.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CresusPage {

    private final WebDriverWait wait;

    // Locators
    private final By cresusPageTitle = By.xpath(
            "//*[contains(@text, 'roi de Lydie')]");

    public CresusPage(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, 10);
    }

    // Actions
    /** Attend que la page Crésus soit affichée */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cresusPageTitle));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
