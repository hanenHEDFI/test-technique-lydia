package com.technicaltest.mobile.pages;

import io.appium.java_client.MobileBy;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.PointerInput.Origin;

import java.time.Duration;
import java.util.Arrays;

public class CarouselPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;
    // Locators
    private final By getStartedButton = By.xpath("//*[@text='Get started']");
    private final By primaryTitle = MobileBy.id("org.wikipedia.alpha:id/primaryTextView");
    private final By skipButton = MobileBy.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button");
    private final By continueButton = MobileBy.id("org.wikipedia.alpha:id/fragment_onboarding_forward_button");

    public CarouselPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Actions
    public boolean isOnCarousel() {
        // on attend que le titre soit visible
        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(primaryTitle));

        // on vérifie le texte pour être sûrs d’être sur le bon écran
        String text = title.getText();
        return text.startsWith("The Free Encyclopedia")
                && driver.findElement(skipButton).isDisplayed()
                && driver.findElement(continueButton).isDisplayed();
    }

    public void swipeThroughCarouselToLast() {
        int numberOfSwipes = 3;

        Dimension size = driver.manage().window().getSize();
        int width = size.getWidth();
        int height = size.getHeight();

        int startX = (int) (width * 0.8);
        int endX = (int) (width * 0.2);
        int y = (int) (height * 0.5);

        for (int i = 0; i < numberOfSwipes; i++) {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            // se placer au point de départ
            swipe.addAction(finger.createPointerMove(
                    Duration.ZERO, Origin.viewport(), startX, y));

            // appui
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

            // glisser vers la gauche
            swipe.addAction(finger.createPointerMove(
                    Duration.ofMillis(400), Origin.viewport(), endX, y));

            // relâcher
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickGetStarted() {
        wait.until(ExpectedConditions.elementToBeClickable(getStartedButton)).click();
    }
}
