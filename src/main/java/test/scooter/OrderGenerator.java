package test.scooter;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;

import static test.scooter.constants.OrderConstants.*;

public class OrderGenerator {
    private Random random = new Random();
    private String getdeliveryDate() {
        String deliveryYear = String.valueOf(MIN_YEAR_VALUE + (int) (Math.random() * (MAX_YEAR_VALUE - MIN_YEAR_VALUE + 1)));
        String deliveryMonth = String.valueOf(MIN_MONTH_VALUE + (int) (Math.random() * (MAX_MONTH_VALUE - MIN_MONTH_VALUE + 1)));
        String deliveryDay = String.valueOf(MIN_DAY_VALUE + (int) (Math.random() * (MAX_DAY_VALUE - MIN_DAY_VALUE + 1)));
        return String.format("%s-%s-%s", deliveryYear, deliveryMonth, deliveryDay);
    }

    public Order random() {
        String firstName = RandomStringUtils.randomAlphabetic(5);
        String lastName = RandomStringUtils.randomAlphabetic(5);
        String address = RandomStringUtils.randomAlphabetic(5);
        int metroStation = random.nextInt(99999);
        String phone = String.valueOf(random.nextInt(999999999));
        int rentTime = random.nextInt(99999);
        String deliveryDate = getdeliveryDate();
        String comment = RandomStringUtils.randomAlphabetic(5);
        String[] color = new String[]{RandomStringUtils.randomAlphabetic(5)};
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
