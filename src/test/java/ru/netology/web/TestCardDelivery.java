package ru.netology.web;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDelivery {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    @Test
    void shouldRegistryDelivery() {
        Configuration.holdBrowserOpen = true;
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 5);
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        String str = format1.format(c.getTime());
        open("http://localhost:9999/");
        $("[placeholder=\"Город\"]").setValue("Самара");
        $("[placeholder=\"Дата встречи\"]").sendKeys(deleteString);
        $("[placeholder=\"Дата встречи\"]").setValue(str);
        $("body").click();
        $("[name=\"name\"]").setValue("Иванов Иван");
        $("[name=\"phone\"]").setValue("+79991119911");
        $("[class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(30));
    }
}
