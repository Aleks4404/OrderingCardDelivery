package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCardDelivery {
    private String openUrl = "http://localhost:9999";
    private String validName = "Иванов Иван";
    private String validPhone = "+79370123456";
    private String city = "Самара";

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate nextDate = date.plusDays(3);

    @Test
    void shouldFillInAllTheFieldsCorrectly() {
        open(openUrl);
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(nextDate));
        form.$("[data-test-id=name] input").setValue(validName);
        form.$("[data-test-id=phone] input").setValue(validPhone);
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(12));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formatter.format(nextDate)));
    }
}