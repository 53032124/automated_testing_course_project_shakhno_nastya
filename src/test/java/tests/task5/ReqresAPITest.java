package tests.task5;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.Person;
import models.User;
import pages.task5.ReqresPage;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverChromeStart;
import utils.ListenerForTests;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ListenerForTests.class)
@Feature("Тесты API Reqres")
public class ReqresAPITest extends DriverChromeStart {
    public static ReqresPage reqresPage;
    private static final Logger logger = LoggerFactory.getLogger(BackEndReqresTest.class);

    @Step("Инициализация страницы")
    public void init(String endpointId){
        logStep("Запуск теста для эндпоинта " + endpointId);
        reqresPage = new ReqresPage(driver);
        driver.get("https://reqres.in/");
        RestAssured.reset();
        RestAssured.baseURI = "https://reqres.in/";
        logStep("Страница загружена успешно");
        reqresPage.setEndPoint(endpointId);
        logStep("Эндпоинт выбран");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Инициализация страницы")
    public void init(String endpointId, boolean check){
        logStep("Запуск теста для эндпоинта " + endpointId);
        reqresPage = new ReqresPage(driver);
        driver.get("https://reqres.in/");
        logStep("Страница загружена успешно");
        reqresPage.setEndPoint(endpointId);
        logStep("Эндпоинт выбран");
    }

    @Step("Проверка запроса")
    public void stepCheckRequest(String requestPath){
        assertEquals(requestPath, reqresPage.getLabelRequest());
        logStep("Запрос проверен");
    }

    @Step("Проверка задержки появления")
    public void stepCheckDelay(Integer delaySeconds){
        logStep("Запуск проверки задержки");
        assertTrue(reqresPage.checkVisibleResponseCode(delaySeconds));
        logStep("Проверка задержки завершена");
    }

    @Step("Проверка ответа")
    public void stepCheckResponse(Integer expectedResponseCode){
        assertEquals(expectedResponseCode, Integer.parseInt(reqresPage.getLabelResponse()));
        logStep("Ответ проверен");
    }

    @Step("Проверка кода ответа")
    public void stepCheckResponseCode(String expectedCode, boolean useRegex){
        if (useRegex)
            assertEquals(expectedCode
                    .replaceAll("\\d+-\\d+-\\d+T\\d{2}:\\d{2}:\\d{2}.\\d+Z", "")
                    .replaceAll("\\d{3}", ""), reqresPage.getLabelResponseCode()
                    .replaceAll("\\d+-\\d+-\\d+T\\d{2}:\\d{2}:\\d{2}.\\d+Z", "").replaceAll("\\d{3}", ""));
        else
            assertEquals(expectedCode, reqresPage.getLabelResponseCode());
        logStep("Код ответа проверен");
    }

    @Step("Проверка кода запроса")
    public void stepCheckRequestCode(String expectedRequest){
        assertEquals(expectedRequest, reqresPage.getLabelRequestCode());
        logStep("Запрос проверен");
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users?page=2")
    @Test
    public void testUsersPage2(){
        init("users");
        stepCheckRequest("/api/users?page=2");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/users?page=2").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/2")
    @Test
    public void testSingleUser(){
        init("users-single");
        stepCheckRequest("/api/users/2");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/users/2").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/23")
    @Test
    public void testUserNotFound(){
        init("users-single-not-found");
        stepCheckRequest("/api/users/23");
        stepCheckResponse(404);
        stepCheckResponseCode(given().when().get("api/users/23").then().extract().asString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/unknown")
    @Test
    public void testUnknown(){
        init("unknown");
        stepCheckRequest("/api/unknown");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/unknown").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/unknown/2")
    @Test
    public void testSingleUnknown(){
        init("unknown-single");
        stepCheckRequest("/api/unknown/2");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/unknown/2").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/unknown/23")
    @Test
    public void testUnknownNotFound(){
        init("unknown-single-not-found");
        stepCheckRequest("/api/unknown/23");
        stepCheckResponse(404);
        stepCheckResponseCode(given().when().get("api/unknown/23").then().extract().asString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users")
    @Test
    public void testCreateUser(){
        init("post");
        stepCheckRequest("/api/users");
        stepCheckResponse(201);
        Person person = Person.builder()
                .name("morpheus")
                .job("leader")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(person).when().post("api/users").then().extract().asPrettyString(), true);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/2 (PUT)")
    @Test
    public void testUpdateUserPut(){
        init("put");
        stepCheckRequest("/api/users/2");
        stepCheckResponse(200);
        Person person = Person.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(person).when().put("api/users/2").then().extract().asPrettyString(), true);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/2 (PATCH)")
    @Test
    public void testUpdateUserPatch(){
        init("patch");
        stepCheckRequest("/api/users/2");
        Person person = Person.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(person).when().patch("api/users/2").then().extract().asPrettyString(), true);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/2 (DELETE)")
    @Test
    public void testDeleteUser(){
        init("delete");
        stepCheckRequest("/api/users/2");
        stepCheckResponse(204);
        stepCheckResponseCode(given().when().delete("api/users/2").then().extract().asPrettyString(), true);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/register")
    @Test
    public void testRegisterSuccessful(){
        init("register-successful");
        stepCheckRequest("/api/register");

        stepCheckRequestCode("{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}");
        stepCheckResponse(200);
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/register").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/register (неуспешно)")
    @Test
    public void testRegisterUnsuccessful(){
        init("register-unsuccessful");
        stepCheckRequest("/api/register");
        stepCheckRequestCode("{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}");
        stepCheckResponse(400);
        User user = User.builder()
                .email("sydney@fife")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/register").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/login")
    @Test
    public void testLoginSuccessful(){
        init("login-successful");
        stepCheckRequest("/api/login");
        stepCheckRequestCode("{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}");
        stepCheckResponse(200);
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/login").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/login (неуспешно)")
    @Test
    public void testLoginUnsuccessful(){
        init("login-unsuccessful");
        stepCheckRequest("/api/login");
        stepCheckRequestCode("{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}");
        stepCheckResponse(400);
        User user = User.builder()
                .email("peter@klaven")
                .build();
        stepCheckResponseCode(given().contentType(ContentType.JSON).body(user).when().post("api/login").then().extract().asPrettyString(), false);
    }


    @Owner("Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users?delay=3")
    @Test
    public void testDelayedResponse(){
        init("delay", true);
        stepCheckDelay(4);
        stepCheckRequest("/api/users?delay=3");
        stepCheckResponse(200);
        stepCheckResponseCode(given().when().get("api/users?delay=3").then().extract().asPrettyString(), false);
    }
}
