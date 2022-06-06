package ru.netology.testmode.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import ru.netology.testmode.data.DataGenerator;
import ru.netology.testmode.data.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.given;


public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id=\"login\"] [class=\"input__control\"][name=\"login\"]").setValue(registeredUser.getLogin()); // input login
        $("[data-test-id=\"password\"] [class=\"input__control\"][name=\"password\"]").setValue(registeredUser.getPassword()); // input password
        $$("button[class*=\"button\"][data-test-id=\"action-login\"]").findBy(text("Продолжить")).click(); // press next button
        $("h2[class*=\"heading\"]").shouldHave(text("Check_Failed_testЛичный кабинет"), Duration.ofMillis(15000)); // assert login//
    }


    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");

        $("[data-test-id=\"login\"] [class=\"input__control\"][name=\"login\"]").setValue(notRegisteredUser.getLogin()); // input login
        $("[data-test-id=\"password\"] [class=\"input__control\"][name=\"password\"]").setValue(notRegisteredUser.getPassword()); // input password
        $$("button[class*=\"button\"][data-test-id=\"action-login\"]").findBy(text("Продолжить")).click(); // press next button
        $("[class=\"notification__content\"]").shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofMillis(15000)); // assert login//

    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");

        $("[data-test-id=\"login\"] [class=\"input__control\"][name=\"login\"]").setValue(blockedUser.getLogin()); // input login
        $("[data-test-id=\"password\"] [class=\"input__control\"][name=\"password\"]").setValue(blockedUser.getPassword()); // input password
        $$("button[class*=\"button\"][data-test-id=\"action-login\"]").findBy(text("Продолжить")).click(); // press next button
        $("[class=\"notification__content\"]").shouldHave(text("Ошибка! Пользователь заблокирован"), Duration.ofMillis(15000)); // assert login//
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.Registration.getRandomLogin();

        $("[data-test-id=\"login\"] [class=\"input__control\"][name=\"login\"]").setValue(wrongLogin); // input login
        $("[data-test-id=\"password\"] [class=\"input__control\"][name=\"password\"]").setValue(registeredUser.getPassword()); // input password
        $$("button[class*=\"button\"][data-test-id=\"action-login\"]").findBy(text("Продолжить")).click(); // press next button
        $("[class=\"notification__content\"]").shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofMillis(15000)); // assert login//

    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.Registration.getRandomPassword();

        $("[data-test-id=\"password\"] [class=\"input__control\"][name=\"password\"]").setValue(registeredUser.getLogin()); // input login
        $("[data-test-id=\"login\"] [class=\"input__control\"][name=\"login\"]").setValue(wrongPassword); // input password
        $$("button[class*=\"button\"][data-test-id=\"action-login\"]").findBy(text("Продолжить")).click(); // press next button
        $("[class=\"notification__content\"]").shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofMillis(15000)); // assert login//

    }

}
