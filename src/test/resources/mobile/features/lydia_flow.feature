Feature: Parcours Lydia et navigation vers Crésus

  Scenario: Rechercher Lydia et ouvrir l'article Crésus
    Given I launch the mobile application

    When I swipe through the carousel until the last image
    And I click on "Get started"
    Then I should be navigated to the "Home page"

    When I search for "Lydia"
    And I scroll until I find the city Lydia
    Then I should see the "popup"

    When I dismiss the popup
    Then I should not see the "popup"

    When I change the website language to "French"
    Then I should see the "new language"

    When I scroll until I find the article "Cresus"
    And I click on "Cresus"
    And I click on "Lire l'article"
    Then I should be navigated to the "Cresus page"

