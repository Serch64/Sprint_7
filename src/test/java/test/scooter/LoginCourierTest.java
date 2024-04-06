package test.scooter;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.scooter.steps.CourierStepsClass;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Логин курьера POST: /api/v1/courier/login")
public class LoginCourierTest extends CourierStepsClass {
    private static final String STATUS_CODE_400_EXPECTED_ERROR = "Недостаточно данных для входа";
    private static final String STATUS_CODE_404_EXPECTED_ERROR = "Учетная запись не найдена";
    @Before
    public void setUp() {
        turnOnLogs();
        prepareTestData();
    }
    @Test
    @DisplayName("Тест на успешную авторизацию")
    @Description("Проверка что курьер может авторизоваться со всеми обязательными полями")
    public void loginCourier() {
        createCourier();
        ValidatableResponse response = login().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        id = getLoginId(response);
    }
    @Test
    @DisplayName("Тест на авторизацию курьера без обязательного поля")
    @Description("Проверка на авторизацию курьера без обязательного поля и получение ошибки")
    public void loginMissField() {
        createInvalidCourier();
        ValidatableResponse response = login().assertThat().body("message", equalTo(STATUS_CODE_400_EXPECTED_ERROR))
                .and()
                .statusCode(400);
        id = getLoginId(response);
    }
    @Test
    @DisplayName("Тест на авторизацию под несуществующим пользователем")
    @Description("Проверка на авторизацию под несуществующим пользователем и получение ошибки")
    public void loginUnknownCourier() {
        createUnknownCourier();
        ValidatableResponse response = login().assertThat().body("message", equalTo(STATUS_CODE_404_EXPECTED_ERROR))
                .and()
                .statusCode(404);
        id = getLoginId(response);
    }

    @After
    public void deleteTestDataAndResetLogs() {
        if (id != null) {
            deleteTestData(id);
        }
        turnOffLogs();
    }
}


