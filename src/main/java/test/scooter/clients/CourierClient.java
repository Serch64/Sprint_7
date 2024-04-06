package test.scooter.clients;

import io.restassured.response.ValidatableResponse;
import test.scooter.Courier;
import test.scooter.CourierCreds;

import static io.restassured.RestAssured.given;

public class CourierClient extends Spec {
    private static final String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_COURIER_ENDPOINT = "/api/v1/courier/login";
    private static final String DELETE_COURIER_ENDPOINT = "/api/v1/courier/";



    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER_ENDPOINT)
                .then();
    }
    public ValidatableResponse login(CourierCreds courierCreds) {
        return given()
                .spec(getSpec())
                .body(courierCreds)
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then();
    }
    public void delete(String id) {
        given().spec(getSpec())
                .delete(DELETE_COURIER_ENDPOINT + id);
    }
}
