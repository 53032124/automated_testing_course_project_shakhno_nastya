package tests;

import pages.CitilinkPage;
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
public class CitilinkTests extends DriverChromeStart {

    CitilinkPage citilinkPage;

    @Owner("Анастасия Шахно")
    @DisplayName("Тест-кейс 1 (проверка сортировки товаров по цене)")
    @Test
    public void testProductSortingByPrice() {
        citilinkPage = new CitilinkPage(driver);
        driver.get(citilinkPage.citilinkURL);

        verifyHomePage();
        navigateToSmartphonesAndTablets();
        logFirstFiveProducts();
        sortProductsByPriceAsc();
    }

    @Owner("Анастасия Шахно")
    @DisplayName("Тест-кейс 2 (добавление товара в корзину с фильтром 'Забрать через 5 минут')")
    @Test
    public void testAddProductToCartWithFilter() {
        citilinkPage = new CitilinkPage(driver);
        driver.get(citilinkPage.citilinkURL);

        verifyHomePage();
        navigateToProcessors();
        applyFiveMinutePickupFilter();
        addFirstProductToCart();
        verifyProductInCart();
    }

    @Step("Шаг 1. Проверить, что открылась главная страница")
    public void verifyHomePage() {
        logStep("Шаг 1: Проверить, что открылась главная страница");

        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Ситилинк - интернет-магазин техники, электроники, товаров для дома и ремонта"));

        logStep("Главная страница успешно проверена \n");
    }

    @Step("Шаг 2. Перейти в категорию 'Смартфоны и планшеты' -> 'APPLE iPhone'")
    public void navigateToSmartphonesAndTablets() {
        logStep("Шаг 2: Перейти в категорию 'Смартфоны и планшеты' -> 'APPLE iPhone'");
        citilinkPage.selectCategory();
        logStep("Страница с техникой Apple загружена \n");
    }

    @Step("Шаг 2. Перейти на страницу Процессоров")
    public void navigateToProcessors() {
        logStep("Шаг 2: Перейти на страницу Процессоров");
        citilinkPage.navigateToProcessors();
        logStep("Страница с процессорами загружена \n");
    }

    @Step("Шаг 3. Применить фильтр 'Забрать через 5 минут'")
    public void applyFiveMinutePickupFilter() {
        logStep("Шаг 3: Применить фильтр 'Забрать через 5 минут'");

        citilinkPage.applyFiveMinutePickupFilter();
        logStep("Фильтр 'Забрать через 5 минут' применен \n");
    }

    @Step("Шаг 4. Добавить первый товар в корзину")
    public void addFirstProductToCart() {
        logStep("Шаг 4: Добавить первый товар в корзину");
        citilinkPage.addFirstProductToCart();
        logStep("Первый товар добавлен в корзину \n");
    }

    @Step("Шаг 5. Перейти в корзину и проверить наличие товара")
    public void verifyProductInCart() {
        logStep("Шаг 5: Перейти в корзину и проверить наличие товара");
        citilinkPage.verifyProductInCart();
        logStep("Товар успешно добавлен в корзину \n");
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
