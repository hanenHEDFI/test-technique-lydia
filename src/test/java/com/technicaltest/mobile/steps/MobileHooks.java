package com.technicaltest.mobile.steps;

import com.technicaltest.mobile.config.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Base64;

public class MobileHooks {

    @Before
    public void startDriverAndVideo() {
        AndroidDriver driver = DriverManager.getDriver();

        // Start recording
        ((CanRecordScreen) driver).startRecordingScreen(
                new AndroidStartScreenRecordingOptions()
                        .withTimeLimit(Duration.ofMinutes(5))
        );
    }

    @After
    public void tearDown(Scenario scenario) {
        AndroidDriver driver = DriverManager.getDriver();

        // Stop video recording
        String videoBase64 = ((CanRecordScreen) driver).stopRecordingScreen();
        byte[] videoBytes = Base64.getDecoder().decode(videoBase64);

        if (scenario.isFailed()) {
            // Capture écran
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "Screenshot on failure");
            Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));

            // Vidéo
            scenario.attach(videoBytes, "video/mp4", "Failure video");
            Allure.addAttachment("Failure video", "video/mp4", new ByteArrayInputStream(videoBytes), ".mp4");
        }

        // Toujours fermer le driver
        DriverManager.quitDriver();
    }
}


