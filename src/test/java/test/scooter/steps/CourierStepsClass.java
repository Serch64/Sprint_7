package test.scooter.steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import test.scooter.Courier;
import test.scooter.clients.CourierClient;
import test.scooter.CourierCreds;

import static test.scooter.constants.CourierTestData.*;

public class CourierStepsClass {
    protected CourierClient courierClient;
    protected Courier courier;
    protected String id;

    @Step("Включение логов")
    public void turnOnLogs() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Step("Подготовка тестовых данных для создания курьера")
    public void prepareTestData() {
        courierClient = new CourierClient();

    }
    @Step("Создание курьера")
    public ValidatableResponse createCourier() {
        courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
       return courierClient.create(courier);
    }
    @Step("Создание несуществующего курьера")
    public void createUnknownCourier() {
        courier = new Courier(RandomStringUtils.randomAlphabetic(5), PASSWORD, FIRST_NAME);
    }
    @Step("Создание курьера c постоянным логином")
    public ValidatableResponse createSameLoginCourier() {
        courier = new Courier(LOGIN, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        return courierClient.create(courier);
    }
    @Step("Создание курьера без обязательного поля")
    public ValidatableResponse createInvalidCourier() {
        courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        courier.setLogin(null);
        return courierClient.create(courier);
    }
    @Step("Авторизация пользователя в системе")
    public ValidatableResponse login() {
        return courierClient.login(CourierCreds.from(courier));
    }
    @Step("Получение login Id")
    public String getLoginId(ValidatableResponse response) {
        return response.extract().jsonPath().getString("id");
    }
    @Step("Удаление курьера")
    public void deleteTestData(String id) {
            courierClient.delete(id);
    }
    @Step("Выключение логов после прогона теста")
    public void turnOffLogs() {
        RestAssured.reset();
    }
}
