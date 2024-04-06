package test.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.scooter.steps.CourierStepsClass;

import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("Создание курьера POST: /api/v1/courier")
public class CreateCourierTest extends CourierStepsClass {

    private static final String STATUS_CODE_409_EXPECTED_ERROR = "Этот логин уже используется. Попробуйте другой.";
    private static final String STATUS_CODE_400_EXPECTED_ERROR = "Недостаточно данных для создания учетной записи";

    @Before
    public void setUp() {
        turnOnLogs();
        prepareTestData();

    }
    @Test
    @DisplayName("Тест на успешное создание курьера")
    @Description("Проверка на успешное создание курьера со всеми обязательными полями")
    public void createNewCourier(){
        createCourier().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        ValidatableResponse response = login();
        id = getLoginId(response);
    }
    @Test
    @DisplayName("Тест на создание двух одинаковых курьеров")
    @Description("Проверка на создание двух одинаковых курьеров и получение ошибки")
    public void createTwoCouriers(){
        createCourier();
        ValidatableResponse response = login();
        id = getLoginId(response);
        createCourier().assertThat().body("message", equalTo(STATUS_CODE_409_EXPECTED_ERROR))
                .and()
                .statusCode(409);
    }
    @Test
    @DisplayName("Тест на создание двух курьеров c логином который уже существует")
    @Description("Проверка на создание двух курьеров c логином который уже существует")
    public void createTwoSameLoginCouriers(){
        createSameLoginCourier();
        ValidatableResponse response = login();
        id = getLoginId(response);
        createSameLoginCourier().assertThat().body("message", equalTo(STATUS_CODE_409_EXPECTED_ERROR))
                .and()
                .statusCode(409);

    }
    @Test
    @DisplayName("Тест на создание курьера без обязательного поля")
    @Description("Проверка на создание курьера без обязательного поля и получение ошибки")
    public void createCourierMissField(){
        createInvalidCourier().assertThat().body("message", equalTo(STATUS_CODE_400_EXPECTED_ERROR))
                .and()
                .statusCode(400);
        ValidatableResponse response = login();
        id = getLoginId(response);
    }
    @After
    public void deleteTestData(){
        if (id != null) {
            deleteTestData(id);
        }
        turnOffLogs();
    }
}
