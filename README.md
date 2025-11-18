# Lydia Technical Test – API & Mobile Automation

This repository contains:

- A **REST API automated test framework** (using Reqres demo API)
- A **Mobile UI automated test framework** (using the Wikipedia Alpha Android app to simulate a flow)
- **Two Allure HTML reports** provided for review:
  - `reports/api-report/index.html`
  - `reports/mobile-report/index.html`

The project is implemented in **Java 17**, using **Maven**, **JUnit 5**, **Rest Assured**, **Appium**, **Cucumber**, and **Allure**.

---

## 1. Project structure

```text
test-technique-lydia
├─ pom.xml
├─ reports/
│  ├─ api-report/
│  └─ mobile-report/
└─ src/
   └─ test/
      ├─ java/
      │  └─ com/technicaltest/
      │     ├─ api/
      │     │  ├─ config/
      │     │  │  └─ ApiConfig.java
      │     │  └─ tests/
      │     │     └─ CreateFetchUser.java
      │     └─ mobile/
      │        ├─ config/
      │        │  └─ DriverManager.java
      │        ├─ pages/
      │        │  ├─ CarouselPage.java
      │        │  ├─ CreussPage.java
      │        │  ├─ HomePage.java
      │        │  ├─ LanguagePage.java
      │        │  ├─ LydiaPage.java
      │        │  └─ SearchPage.java
      │        ├─ runners/
      │        │  └─ MobileTestRunner.java
      │        └─ steps/
      │           ├─ LydiaSteps.java
      │           └─ MobileHooks.java
      └─ resources/
         ├─ api/
         │  └─ api-config.properties
         └─ mobile/
            ├─ app/
            │  └─ alpha.apk
            └─ features/
               └─ lydia_flow.feature
```

---

## 2. Prerequisites

### General
- **Java 17**
- **Maven 3.8+**

### API Tests
Nothing to install.  
API config file:  
`src/test/resources/api/api-config.properties`

Example:

```properties
base.uri=https://reqres.in
api.key=reqres-free-v1

register.email=eve.holt@reqres.in
register.password=pistol
register.first_name=Eve
register.last_name=Holt
```

### Mobile Tests
You need:

- Android SDK installed
- A running Android emulator (ex: `emulator-5554`)  
- **Appium Server** running on: `http://127.0.0.1:4723/wd/hub`

Mobile driver configuration is in:  
`src/test/java/com/technicaltest/mobile/config/DriverManager.java`

---

## 3. API Test Framework

### Test scenario: `CreateFetchUser.java`

Flow:

1. **Register user**  
   - POST `/api/register`  
   - Email & password from `api-config.properties`  
   - Validate response, store generated user ID  

2. **Fetch user**  
   - GET `/api/users/{id}`  
   - Validate email, names, avatar, status code  

### Run API tests:

```bash
mvn clean test "-Dtest=CreateFetchUser" "-Dallure.results.directory=target/allure-results-api"
```

### Generate Allure report (static HTML)

```bash
mvn allure:report "-Dallure.results.directory=allure-results-api" "-Dallure.report.directory=reports/api-report"
```


### View report with live server (recommended):

```bash
mvn allure:serve "-Dallure.results.directory=allure-results-api"
```

---

## 4. Mobile Automation Framework (Wikipedia Alpha App)

The UI test simulates a simplified version of a "Lydia-like" onboarding flow using the **Wikipedia Alpha app**.

### Feature file:
`src/test/resources/mobile/features/lydia_flow.feature`

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

Cucumber step definitions:  
`com.technicaltest.mobile.steps`

Pages:  
`com.technicaltest.mobile.pages`

Test runner:  
`MobileTestRunner.java`

### Run mobile tests:

```bash
mvn test "-Dtest=MobileTestRunner" "-Dallure.results.directory=target/allure-results-mobile"
```

### Generate Allure report:

```bash
mvn allure:report "-Dallure.results.directory=allure-results-mobile" "-Dallure.report.directory=reports/mobile-report"
```

Open:

```
reports/mobile-report/index.html
```

### Live view:

```bash
mvn allure:serve "-Dallure.results.directory=allure-results-mobile"
```

---

## 5. How to reproduce both test suites

### API Suite

```bash
mvn clean test "-Dtest=CreateFetchUser" "-Dallure.results.directory=target/allure-results-api"
mvn allure:report "-Dallure.results.directory=allure-results-api" "-Dallure.report.directory=reports/api-report"
```

### Mobile Suite

Start emulator → Start Appium → Run:

```bash
mvn test "-Dtest=MobileTestRunner" "-Dallure.results.directory=target/allure-results-mobile"
mvn allure:report "-Dallure.results.directory=allure-results-mobile" "-Dallure.report.directory=reports/mobile-report"
```

---

## 6. Author

Technical test implementation by **Hanen Hedfi**  
(QA Automation Engineer)
