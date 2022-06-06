package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //set date format


        public static String generateLogin(String locale) {
            Faker faker = new Faker(new Locale(locale));
            String login = faker.letterify("???????????");
            return login;
        }

        public static String generatePassword(String locale) {
            Faker faker = new Faker(new Locale(locale));
            String password = faker.internet().password();
            return password;
        }

        public static RegistrationInfo getRegisteredUser(String status) {
            String login = DataGenerator.Registration.generateLogin("en");
            String password = DataGenerator.Registration.generatePassword("en");
            DataGenerator.Registration.userRegistration(login, password, status);
            return new RegistrationInfo(login, password, status);
        }

        public static RegistrationInfo getUser(String status) {
            String login = DataGenerator.Registration.generateLogin("en");
            String password = DataGenerator.Registration.generatePassword("en");
            return new RegistrationInfo(login, password, status);
        }

        public static String getRandomLogin() {
            String login = DataGenerator.Registration.generateLogin("en");
            return login;
        }

        public static String getRandomPassword() {
            String password = DataGenerator.Registration.generatePassword("en");
            return password;
        }

        private static void userRegistration(String login, String password, String status) {

            JsonObject rootObject = new JsonObject(); // создаем главный объект
            rootObject.addProperty("login", login); // записываем текст в поле "message"
            rootObject.addProperty("password", password); // записываем текст в поле "name" у объект Place
            rootObject.addProperty("status", status);

            Gson gson = new Gson();
            String json = gson.toJson(rootObject); // генерация json строки

            given()
                    .baseUri("http://localhost:9999")
                    .contentType("application/json; charset=UTF-8")
                    .body(json) // отправляемые данные (заголовки и query можно выставлять аналогично)

                    .when()
                    .post("/api/system/users")

                    .then()
                    .statusCode(200)
            ;
        }

    }
}






