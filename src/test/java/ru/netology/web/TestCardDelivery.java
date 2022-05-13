package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDelivery {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    @Test
    void shouldRegistryDelivery() {

        Configuration.holdBrowserOpen = true;
        String planningDate = generateDate(4);

        open("http://localhost:9999/");
        $("[placeholder=\"Город\"]").setValue("Самара");
        $("[placeholder=\"Дата встречи\"]").sendKeys(deleteString);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79991119911");
        $("[class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15));
    }
}
