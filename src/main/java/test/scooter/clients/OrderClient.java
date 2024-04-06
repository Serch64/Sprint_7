package test.scooter.clients;

import io.restassured.response.ValidatableResponse;
import test.scooter.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends Spec {
    private static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";
    private static final String CANCEL_ORDER_ENDPOINT = "/api/v1/orders/cancel";
    private static final String GET_ORDERS_ENDPOINT = "/api/v1/orders";

    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }
    public void cancel(String track) {
        given()
                .spec(getSpec())
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER_ENDPOINT)
                .then();
    }
    public ValidatableResponse getOrders() {
        return given()
                .spec(getSpec())
                .get(GET_ORDERS_ENDPOINT)
                .then();
    }
}
