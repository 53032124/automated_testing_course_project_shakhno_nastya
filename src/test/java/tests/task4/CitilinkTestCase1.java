package tests.task4;

import pages.task4.CitilinkPage;

import utils.DriverChromeStart;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ListenerForTests;

@Feature("Задание 4")
@ExtendWith(ListenerForTests.class)
public class CitilinkTestCase1 extends DriverChromeStart {

    CitilinkPage citilinkPage;

    @Owner("Анастасия Шахно")
    @DisplayName("Тест-кейс 1 (проверка сортировки товаров по цене)")
    @Test
    public void testProductSortingByPrice() {
        citilinkPage = new CitilinkPage(driver);
        driver.get(citilinkPage.citilinkURL);

        verifyHomePage();
        navigateToInternalHardDrives();
        logFirstFiveProducts();
        sortProductsByPriceAsc();
    }

    @Step("Шаг 1. Проверить, что открылась главная страница")
    protected static void verifyHomePage() {
        logStep("Шаг 1: Проверить, что открылась главная страница");

        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Ситилинк - интернет-магазин техники, электроники, товаров для дома и ремонта"));

        logStep("Главная страница успешно проверена \n");
    }

    @Step("Шаг 2. Перейти в категорию 'Смартфоны и планшеты' -> 'APPLE iPhone'")
    public void navigateToInternalHardDrives() {
        logStep("Шаг 2: Перейти в категорию 'Смартфоны и планшеты' -> 'APPLE iPhone'");
        citilinkPage.selectCategory();

        logStep("Страница с техникой Apple загружена \n");
    }

    @Step("Шаг 3. Вывести в лог первые 5 найденных товаров")
    public void logFirstFiveProducts() {
        logStep("Шаг 3: Вывести в лог первые 5 найденных товаров");

        citilinkPage.logFirstFiveProducts();

        logStep("Логи выведены \n");

    }

    @Step("Шаг 4. Установить сортировку: подешевле")
    public void sortProductsByPriceAsc() {
        logStep("Шаг 4: Установить сортировку: подешевле");

        citilinkPage.sortByPriceAsc();

        logStep("Сортировка по цене успешно установлена \n");
    }



}
