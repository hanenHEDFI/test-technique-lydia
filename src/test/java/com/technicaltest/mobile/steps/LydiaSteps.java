package com.technicaltest.mobile.steps;

import com.technicaltest.mobile.config.DriverManager;
import com.technicaltest.mobile.pages.CarouselPage;
import com.technicaltest.mobile.pages.HomePage;
import com.technicaltest.mobile.pages.SearchPage;
import com.technicaltest.mobile.pages.LydiaPage;
import com.technicaltest.mobile.pages.LanguagePage;
import com.technicaltest.mobile.pages.CresusPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LydiaSteps {

    private final AndroidDriver driver;

    private final CarouselPage carouselPage;
    private final HomePage homePage;
    private final SearchPage searchPage;
    private final LydiaPage lydiaPage;
    private final LanguagePage languagePage;
    private final CresusPage cresusPage;

    public LydiaSteps() {
        this.driver = DriverManager.getDriver();
        this.carouselPage = new CarouselPage(driver);
        this.homePage = new HomePage(driver);
        this.searchPage = new SearchPage(driver);
        this.lydiaPage = new LydiaPage(driver);
        this.languagePage = new LanguagePage(driver);
        this.cresusPage = new CresusPage(driver);
    }

    // -------------------------------------------------------------------------
    // GIVEN
    // -------------------------------------------------------------------------

    @Given("I launch the mobile application")
    public void iLaunchTheMobileApplication() {
        // Le simple fait d'appeler DriverManager.getDriver() dans le constructeur
        // lance déjà l'application via les capabilities (app ou
        // appPackage/appActivity).
        // Tu peux ajouter une petite vérification ici si tu veux.
        Assert.assertTrue("Carousel page should be loaded", carouselPage.isOnCarousel());
    }

    // -------------------------------------------------------------------------
    // WHEN: CAROUSEL
    // -------------------------------------------------------------------------

    @When("I swipe through the carousel until the last image")
    public void iSwipeThroughTheCarouselUntilTheLastImage() {
        carouselPage.swipeThroughCarouselToLast();
    }

    @When("I click on {string}")
    public void iClickOn(String label) {
        switch (label) {
            case "Get started":
                carouselPage.clickGetStarted();
                break;

            case "Cresus":
                lydiaPage.ClickOn(label);
                break;

            case "Lire l'article":
                lydiaPage.ClickOn(label);
                break;

            default:
                throw new IllegalArgumentException("Unknown label in step I click on: " + label);
        }
    }

    // -------------------------------------------------------------------------
    // WHEN: SEARCH / Lydia
    // -------------------------------------------------------------------------

    @When("I search for {string}")
    public void iSearchFor(String query) {
        searchPage.searchFor(query);
    }

    @When("I scroll until I find the city Lydia")
    public void iScrollUntilIFindTheCity() {
        searchPage.clickLydiaAncientKingdom();
    }

    @When("I dismiss the popup")
    public void iDismissTheCityDetailsPopup() {
        lydiaPage.dismissPopup();
    }

    // -------------------------------------------------------------------------
    // WHEN: LANGUAGE / ARTICLE / SCROLL
    // -------------------------------------------------------------------------

    @When("I change the website language to {string}")
    public void iChangeTheWebsiteLanguageTo(String language) {
        languagePage.changeLanguageTo(language);
    }

    @When("I scroll until I find the article {string}")
    public void iScrollUntilIFindTheArticle(String articleTitle) {
        lydiaPage.scrollToArticle(articleTitle);
    }

    // -------------------------------------------------------------------------
    // THEN: NAVIGATION
    // -------------------------------------------------------------------------

    @Then("I should be navigated to the {string}")
    public void iShouldBeNavigatedToThe(String pageName) {
        switch (pageName) {
            case "Home page":
                Assert.assertTrue("Home page should be displayed", homePage.isLoaded());
                break;

            case "Cresus page":
                Assert.assertTrue("Crésus page should be displayed", cresusPage.isLoaded());
                break;

            default:
                throw new IllegalArgumentException("Unknown page name in step I should be navigated to: " + pageName);
        }
    }

    // -------------------------------------------------------------------------
    // THEN: VISIBILITY OF ELEMENTS / POPUPS / LANGUAGE
    // -------------------------------------------------------------------------

    @Then("I should see the {string}")
    public void iShouldSeeThe(String target) {
        switch (target) {
            case "popup":
                Assert.assertTrue("popup should be visible",
                        lydiaPage.isDetailsPopupVisible());
                break;

            case "new language":
                // Par exemple : vérifier que la langue active est le français
                Assert.assertTrue("New language (French) should be applied",
                        lydiaPage.isFrenchLoaded());
                break;

            default:
                throw new IllegalArgumentException("Unknown target in step I should see the: " + target);
        }
    }

    @Then("I should not see the {string}")
    public void iShouldNotSeeThe(String target) {
        switch (target) {
            case "popup":
                Assert.assertFalse("City details popup should not be visible",
                        lydiaPage.isDetailsPopupVisible());
                break;

            default:
                throw new IllegalArgumentException("Unknown target in step I should not see the: " + target);
        }
    }
}
