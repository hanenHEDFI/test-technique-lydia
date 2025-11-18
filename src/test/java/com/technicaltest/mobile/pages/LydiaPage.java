package com.technicaltest.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LydiaPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By lydiaTitleFrench = By
            .xpath("//*[contains(@text, \"ancien pays d'Asie Mineure\") or contains(@text, 'Lydie')]");
    private final By cresusLink = By.xpath("//*[@text='Crésus' or contains(@text, 'Crésus')]");
    private final By popupContainer = By.id("org.wikipedia.alpha:id/dialogContainer");
    private final By closeButton = By.id("org.wikipedia.alpha:id/closeButton");
    private final By readArticleButton = By.id("org.wikipedia.alpha:id/link_preview_primary_button");

    public LydiaPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Actions
    public boolean isDetailsPopupVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(popupContainer));
            return driver.findElement(popupContainer).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Ferme le popup en cliquant sur la croix */
    public void dismissPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        // on attend que le popup disparaisse
        wait.until(ExpectedConditions.invisibilityOfElementLocated(popupContainer));
    }

    public boolean isFrenchLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(lydiaTitleFrench));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void ClickOn(String label) {
        switch (label) {

            case "Cresus":
                // on scrolle d’abord jusqu’à voir l’article
                scrollToArticle(label);
                // puis on clique
                wait.until(ExpectedConditions.elementToBeClickable(cresusLink)).click();
                break;

            case "Lire l'article":
                wait.until(ExpectedConditions.elementToBeClickable(readArticleButton)).click();
                break;
        }

    }

    /**
     * Utilise UiScrollable pour faire défiler la page jusqu’à trouver un texte
     */
    public void scrollToArticle(String articleTitle) {
        if (articleTitle.equalsIgnoreCase("Cresus")) {
            articleTitle = "Crésus";
        }
        String uiScrollable = "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"" + articleTitle + "\"));";

        driver.findElementByAndroidUIAutomator(uiScrollable);
    }
}
