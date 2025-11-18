package com.technicaltest.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LanguagePage {

    private final WebDriverWait wait;

    // Locators
    private final By languageMenu = By.id("org.wikipedia.alpha:id/page_language");
    private final By searchButton = By.xpath("//*[@content-desc='Search']");
    private final By searchInput = By.className("android.widget.EditText");
    private final By frenchResult = By.xpath("//*[contains(@text,'French') or contains(@text,'Français')]");

    public LanguagePage(AndroidDriver driver) {

        this.wait = new WebDriverWait(driver, 10);
    }

    // Actions
    public void changeLanguageTo(String language) {

        // 1. Ouvrir menu langues
        wait.until(ExpectedConditions.elementToBeClickable(languageMenu)).click();

        // 2. Attendre que le bouton recherche soit visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton)).click();

        // 3. Lorsque le champ est visible, taper le texte demandé
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(language);

        // 4. Cliquer le résultat affiché
        WebElement result = wait.until(ExpectedConditions.elementToBeClickable(frenchResult));
        result.click();
    }
}
