package test.scooter.steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import test.scooter.Order;
import test.scooter.OrderGenerator;
import test.scooter.clients.OrderClient;

public class OrderStepsClass {
    protected OrderClient orderClient;
    protected Order order;
    protected String track;
    protected String colorForAllure;
    protected String[] color;

    @Step("Включение логов")
    public void turnOnLogs() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Step("Подготовка тестовых данных для создания заказа")
    public void prepareTestData() {
        orderClient = new OrderClient();
        OrderGenerator orderGenerator = new OrderGenerator();
        order = orderGenerator.random();
    }
    @Step("Подготовка тестовых данных для получения списка заказов")
    public void prepareTestDataOrderList() {
        orderClient = new OrderClient();
    }
    @Step("Изменение цвета под параметризацию")
    public void changeColor() {
        order.setColor(color);
    }
    @Step("Создание заказа")
    public ValidatableResponse createOrder() {
        return orderClient.create(order);
    }
    @Step("Получение track")
    public String getTrack(ValidatableResponse response) {
        return response.extract().jsonPath().getString("track");
    }
    @Step("Отмена заказа")
    public void cancelOrder(String track) {
        orderClient.cancel(track);
    }
    @Step("Получение списка заказа")
    public ValidatableResponse getOrderList() {
        return orderClient.getOrders();
    }
    @Step("Выключение логов после прогона теста")
    public void turnOffLogs() {
        RestAssured.reset();
    }
}
