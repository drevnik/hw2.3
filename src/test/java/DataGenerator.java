import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static UserInfo createValidUserInfo() {
        Faker faker = new Faker(new Locale("ru"));
        return new UserInfo(
                getCity(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber()
        );
    }

    public static String getCity() {
        List<String> city = new ArrayList<>();
        city.add("Астрахань");
        city.add("Кострома");
        city.add("Краснодар");
        city.add("Москва");
        city.add("Салехард");
        city.add("Смоленск");
        city.add("Тамбов");
        city.add("Тверь");
        city.add("Томск");
        city.add("Тула");
        city.add("Тюмень");
        city.add("Ульяновск");
        city.add("Челябинск");
        city.add("Ярославль");
        city.add("Магадан");
        city.add("Москва");
        city.add("Новосибирск");
        city.add("Воронеж");
        city.add("Владимир");
        city.add("Воронеж");
        city.add("Владимир");

        Random random = new Random();
        int index = random.nextInt(19);

        return city.get(index);
    }

    public static int getMinimumDaysToDelivery() {
        return 3;
    }

    public static LocalDate getEarliestValidDate() {
        return LocalDate.now().plusDays(getMinimumDaysToDelivery());
    }

    public static long getTimeStampString(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.atStartOfDay(zoneId).toEpochSecond();
    }
}