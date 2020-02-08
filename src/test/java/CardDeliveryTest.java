import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Отображается уведомление 'Успешно', если все поля заполнены валидными данными")
    void shouldBeSuccessIfDataIsValid() {
        open("http://localhost:9999/");

        UserInfo validUser = DataGenerator.createValidUserInfo();

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getFullName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$(".button").findBy(Condition.exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible, 3000);
    }

    @Test
    @DisplayName("Отображается уведомление 'Перепланировать', если изменена дата")
    void planAgainIfDateWasChanged() {
        open("http://localhost:9999/");

        UserInfo validUser = DataGenerator.createValidUserInfo();
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getFullName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$(".button").findBy(Condition.exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible, 3000);
        $("button .icon_name_calendar").click();
        $(".calendar__layout").waitUntil(Condition.visible, 2000);
        long timestamp = DataGenerator.getTimeStampString(DataGenerator.getEarliestValidDate().plusDays(1));
        $(String.format("[data-day='%d000']", timestamp)).click();
        $$(".button").findBy(Condition.exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").waitUntil(Condition.visible, 3000);

    }
}