package com.technicaltest.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriverWait wait;

    // Locators
    private final By searchContainer = By.id("org.wikipedia.alpha:id/search_container");
    private final By exploreTab = By.id("org.wikipedia.alpha:id/nav_tab_explore");

    public HomePage(AndroidDriver driver) {

        this.wait = new WebDriverWait(driver, 10);
    }

    // Actions
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchContainer));
            wait.until(ExpectedConditions.visibilityOfElementLocated(exploreTab));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
