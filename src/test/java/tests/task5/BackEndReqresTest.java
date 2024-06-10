package tests.task5;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.DriverChromeStart;

import java.util.concurrent.TimeUnit;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.hamcrest.Matchers.*;

@Feature("Тестирование REST API reqres")
public class BackEndReqresTest extends DriverChromeStart {

    @Step("Инициализация")
    @BeforeEach
    public void setup() {
        RestAssured.reset();
        RestAssured.baseURI = "https://reqres.in/";
        JsonSchemaValidator.settings = settings()
                .with()
                .jsonSchemaFactory(
                        JsonSchemaFactory
                                .newBuilder()
                                .setValidationConfiguration(ValidationConfiguration
                                        .newBuilder()
                                        .setDefaultVersion(DRAFTV4)
                                        .freeze())
                                .freeze())
                .and()
                .with()
                .checkedValidation(false);

        logStep("Инициализация URI");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users?page=2")
    @Test
    public void testGetUsersFromPage() {
        UserFromPage usersFromPage =
                given()
                        .when()
                        .get("api/users?page=2")
                        .then()
                        .log().all()
                        .body(matchesJsonSchemaInClasspath("jsonSchema/user-from-page.json"))
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.first_name", hasItem("Tobias"))
                        .body("data.last_name", hasItem("Funke"))
                        .statusCode(200)
                        .extract()
                        .as(UserFromPage.class);
        logStep("Запрос успешен");
        Assertions.assertThat(usersFromPage.getPage()).isEqualTo(2);
        Assertions.assertThat(usersFromPage.getPer_page()).isEqualTo(6);
        Assertions.assertThat(usersFromPage.getTotal()).isEqualTo(12);
        Assertions.assertThat(usersFromPage.getTotalPages()).isEqualTo(2);
    }

    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/2")
    @Test
    public void testGetUserById() {
        UserData userData =
                given()
                        .when()
                        .get("api/users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("jsonSchema/user-data.json"))
                        .extract()
                        .response()
                        .getBody().jsonPath().getObject("data", UserData.class);
        logStep("Запрос успешен");
        Assertions.assertThat(userData.getId()).isEqualTo(2);
        Assertions.assertThat(userData.getEmail()).isEqualTo("janet.weaver@reqres.in");
        Assertions.assertThat(userData.getFirstName()).isEqualTo("Janet");
        Assertions.assertThat(userData.getLastName()).isEqualTo("Weaver");
        Assertions.assertThat(userData.getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/users/22")
    @Test
    public void testGetNonexistentUser() {
        Response response = given()
                .when()
                .get("api/users/22")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
        logStep("Запрос успешен");
        Assertions.assertThat(response.asString()).isEqualTo("{}");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/unknown")
    @Test
    public void testGetUnknown() {
        ColorFromPage colorsFromPage =
                given()
                        .when()
                        .get("api/unknown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("jsonSchema/color-from-page.json"))
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.name", hasItem("true red"))
                        .body("data.year", hasItem(2002))
                        .extract()
                        .as(ColorFromPage.class);
        logStep("Запрос успешен");
        Assertions.assertThat(colorsFromPage.getPage()).isEqualTo(1);
        Assertions.assertThat(colorsFromPage.getPer_page()).isEqualTo(6);
        Assertions.assertThat(colorsFromPage.getTotal()).isEqualTo(12);
        Assertions.assertThat(colorsFromPage.getTotalPages()).isEqualTo(2);
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/unknown/2")
    @Test
    public void testGetUnknownById() {
        ColorData colorData =
                given()
                        .when()
                        .get("api/unknown/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("jsonSchema/color-data.json"))
                        .extract()
                        .response()
                        .getBody().jsonPath().getObject("data", ColorData.class);
        logStep("Запрос успешен");
        Assertions.assertThat(colorData.getId()).isEqualTo(2);
        Assertions.assertThat(colorData.getName()).isEqualTo("fuchsia rose");
        Assertions.assertThat(colorData.getYear()).isEqualTo(2001);
        Assertions.assertThat(colorData.getColor()).isEqualTo("#C74375");
        Assertions.assertThat(colorData.getPantoneValue()).isEqualTo("17-2031");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта /api/unknown/23")
    @Test
    public void testGetNonexistentUnknown() {
        Response response = given()
                .when()
                .get("api/unknown/23")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
        logStep("Запрос успешен");
        Assertions.assertThat(response.asString()).isEqualTo("{}");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта создания пользователя /api/users post")
    @Test
    public void testCreateUser() {
        Person person = Person.builder()
                .name("Anastasia")
                .job("Testing")
                .build();
        PeopleCreate createdPerson = given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-create.json"))
                .extract()
                .as(PeopleCreate.class);
        logStep("Запрос успешен");
        Assertions.assertThat(createdPerson.getName()).isEqualTo(person.getName());
        Assertions.assertThat(createdPerson.getJob()).isEqualTo(person.getJob());
    }

    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта обновления пользователя /api/users/2 put")
    @Test
    public void testUpdateUser() {
        Person person = Person.builder()
                .name("Anastasia")
                .job("Testing")
                .build();
        PeopleUpdate updatedPerson = given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .put("api/users/2")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-update.json"))
                .extract()
                .as(PeopleUpdate.class);
        logStep("Запрос успешен");
        Assertions.assertThat(updatedPerson.getName()).isEqualTo(person.getName());
        Assertions.assertThat(updatedPerson.getJob()).isEqualTo(person.getJob());
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта обновления пользователя /api/users/2 patch")
    @Test
    public void testUpdateUserPatch() {
        Person person = Person.builder()
                .name("Anastasia")
                .job("Testing")
                .build();
        PeopleUpdate updatedPerson = given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .patch("api/users/2")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-update.json"))
                .extract()
                .as(PeopleUpdate.class);
        logStep("Запрос успешен");
        Assertions.assertThat(updatedPerson.getName()).isEqualTo(person.getName());
        Assertions.assertThat(updatedPerson.getJob()).isEqualTo(person.getJob());
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта удаления пользователя /api/users/2")
    @Test
    public void testDeleteUser() {
        Response response = given()
                .when()
                .delete("api/users/2")
                .then()
                .log().all()
                .statusCode(204)
                .extract()
                .response();
        logStep("Запрос успешен");
        Assertions.assertThat(response.asString()).isEqualTo("");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта регистрации пользователя /api/register")
    @Test
    public void testRegisterUser() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        UserRegister userRegister = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/register-success.json"))
                .body("id", not(hasItem(nullValue())))
                .body("token", not(hasItem(nullValue())))
                .extract()
                .as(UserRegister.class);
        logStep("Запрос успешен");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта регистрации пользователя /api/register с ошибкой")
    @Test
    public void testRegisterUserError() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .build();
        String errorMessage = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/register")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("jsonSchema/error.json"))
                .extract()
                .response()
                .jsonPath()
                .get("error");
        logStep("Запрос успешен");
        Assertions.assertThat(errorMessage).isEqualTo("Missing password");
    }


    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта авторизации пользователя /api/login")
    @Test
    public void testLoginUser() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        UserLogin userLogin = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/login")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/login.json"))
                .body("token", not(hasItem(nullValue())))
                .extract()
                .as(UserLogin.class);
        logStep("Запрос успешен");
    }

    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта авторизации пользователя /api/login с ошибкой")
    @Test
    public void testLoginUserError() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .build();
        String errorMessage = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/login")
                .then()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("jsonSchema/error.json"))
                .extract()
                .response()
                .jsonPath()
                .get("error");
        logStep("Запрос успешен");
        Assertions.assertThat(errorMessage).isEqualTo("Missing password");
    }

    @Owner(value = "Анастасия Шахно")
    @DisplayName("Проверка эндпоинта задержки /api/users?delay=3")
    @Test
    public void testDelayResponse() {
        Long responseTime = given()
                .when()
                .get("api/users?delay=3")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchema/user-from-page.json"))
                .extract()
                .timeIn(TimeUnit.SECONDS);
        logStep("Запрос успешен");
        Assertions.assertThat(responseTime).isBetween(3L, 4L);
    }
}
