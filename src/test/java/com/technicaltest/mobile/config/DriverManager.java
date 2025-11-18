package com.technicaltest.mobile.config;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    // Instance unique de driver pour tout le projet
    private static AndroidDriver driver;

    // Constructeur privé (on ne veut pas instancier DriverManager)
    private DriverManager() {
        // empty
    }

    // Méthode publique utilisée partout dans les steps/pages
    public static AndroidDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    // Création du driver Appium
    private static AndroidDriver createDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");

        // Nom exact de l’émulateur (adb devices → emulator-5554)
        caps.setCapability("deviceName", "emulator-5554");

        // APK local
        caps.setCapability("app",
                System.getProperty("user.dir") + "/src/test/resources/mobile/app/alpha.apk");

        // Package de l'app
        caps.setCapability("appPackage", "org.wikipedia.alpha");

        // Activity LAUNCHER
        caps.setCapability("appActivity", "org.wikipedia.main.MainActivity");

        // Attendre aussi l’onboarding
        caps.setCapability("appWaitActivity",
                "org.wikipedia.onboarding.InitialOnboardingActivity,org.wikipedia.main.MainActivity,*");

        // Options pratiques
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("newCommandTimeout", 300);

        try {
            return new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    // Fermeture propre du driver
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
