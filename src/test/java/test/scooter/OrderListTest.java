package test.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.scooter.steps.OrderStepsClass;

import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Список заказов GET: /api/v1/orders")
public class OrderListTest extends OrderStepsClass {
    @Before
    public void setUp() {
        turnOnLogs();
        prepareTestDataOrderList();
    }

    @Test
    @DisplayName("Тест на получение списка заказов")
    @Description("Проверка получения списка заказов")
    public void checkOrderList(){
        getOrderList().assertThat().body("orders",  notNullValue())
                .and()
                .statusCode(200);
    }
    @After
    public void resetLogs(){
        turnOffLogs();
    }
}
