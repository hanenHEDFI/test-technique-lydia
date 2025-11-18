package com.technicaltest.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    private final WebDriverWait wait;

    public SearchPage(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, 10);
    }

    //Locators 
    private final By searchContainer = By.id("org.wikipedia.alpha:id/search_container");
    private final By searchInput = By.id("org.wikipedia.alpha:id/search_src_text");
    private final By lydiaAncientKingdomResult = By.xpath(
            "//android.widget.TextView[@text='Ancient Anatolian kingdom']");

    // Actions 
    public void searchFor(String query) {

        // Cliquer sur la barre de recherche
        WebElement container = wait.until(
                ExpectedConditions.elementToBeClickable(searchContainer));
        container.click();

        // Maintenant le champ input apparaît
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput));

        input.clear();
        input.sendKeys(query);

    }

    public void clickLydiaAncientKingdom() {
        WebElement result = wait.until(
                ExpectedConditions.visibilityOfElementLocated(lydiaAncientKingdomResult));

        result.click();
    }
}
