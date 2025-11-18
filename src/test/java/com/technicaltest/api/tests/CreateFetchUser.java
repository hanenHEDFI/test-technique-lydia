package com.technicaltest.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import com.technicaltest.api.config.ApiConfig;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("API Users")
@Feature("Register & Fetch user")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(AllureJunit5.class)
public class CreateFetchUser {

        private Integer userId;
        private static final String API_KEY = ApiConfig.getApiKey();

        @BeforeAll
        void setup() {
                RestAssured.baseURI = ApiConfig.getBaseUri();
                RestAssured.filters(new AllureRestAssured());
        }

        String email = ApiConfig.getRegisterEmail();
        String password = ApiConfig.getRegisterPassword();
        String first_name = ApiConfig.getRegisterFirst_name();
        String last_name = ApiConfig.getRegisterLast_name();

        @Test
        @Order(1)
        @Description("1) Register user and store ID")
        void createUser_andStoreId() {

                String requestBody = "{\n" +
                                "  \"email\": \"" + email + "\",\n" +
                                "  \"password\": \"" + password + "\"\n" +
                                "}";

                Response response = given()
                                .header("x-api-key", API_KEY)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post("/api/register")
                                .then()
                                .statusCode(200)
                                .body("id", notNullValue())
                                .body("token", notNullValue())
                                .extract()
                                .response();

                userId = response.jsonPath().getInt("id");
                System.out.println("Registered user id = " + userId);

                Assertions.assertNotNull(userId);
        }

        @Test
        @Order(2)
        @Description("2) Fetch user with stored ID")
        void getUser_usingStoredId() {
                Assertions.assertNotNull(userId, "userId should not be null");

                given()
                                .header("x-api-key", API_KEY)
                                .accept(ContentType.JSON)
                                .when()
                                .get("/api/users/" + userId)
                                .then()
                                .statusCode(200)
                                .body("data.id", equalTo(userId))
                                .body("data.email", equalTo(email))
                                .body("data.first_name", equalTo(first_name))
                                .body("data.last_name", equalTo(last_name))
                                .body("data.avatar", not(emptyOrNullString()));
        }
}
