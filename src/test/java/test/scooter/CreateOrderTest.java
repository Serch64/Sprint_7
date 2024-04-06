package test.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.scooter.steps.OrderStepsClass;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Создание заказа POST: /api/v1/orders")
public class CreateOrderTest extends OrderStepsClass {
    public CreateOrderTest(String colorForAllure, String[] color) {
        this.colorForAllure = colorForAllure;
        this.color = color;
    }
    @Parameterized.Parameters(name = "Тест {index}. Цвет самоката: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Черный", new String[]{"BLACK"}},
                {"Серый",new String[]{"GREY"}},
                {"Черный и Серый",new String[]{"BLACK", "GREY"}},
                {"Без цвета",new String[]{}}
        };
    }
    @Before
    public void setUp() {
        turnOnLogs();
        prepareTestData();
    }

    @Test
    @Description("Проверка создания заказа с указанием разных цветов самоката")
    public void createNewOrder() {
        changeColor();
        ValidatableResponse response = createOrder().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
        track = getTrack(response);
    }
    @After
    public void deleteTestData() {
        if (track != null) {
            cancelOrder(track);
        }
        turnOffLogs();
    }
}
