package tests.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.task4.CitilinkPageForTestCase3;
import utils.ListenerForTests;
import utils.DriverChromeStart;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;

@Feature("Задание 4")
@ExtendWith(ListenerForTests.class)
public class CitilinkTestCase3 extends DriverChromeStart {

    CitilinkPageForTestCase3 citilinkPage;

    @Owner("Анастасия Шахно")
    @DisplayName("Тест-кейс 3 (добавление уцененного товара в избранное и проверка его отображения)")
    @Test
    public void testAddDiscountedProductToFavorites() {
        citilinkPage = new CitilinkPageForTestCase3(driver);
        driver.get(citilinkPage.citilinkURL);

        CitilinkTestCase1.verifyHomePage();
        navigateToDiscountedProducts();
        navigateToDiscountedSmartphones();
        rememberFirstProductDetails();
        addFirstProductToFavorites();
    }

    @Step("Шаг 2. Перейти на страницу 'Каталог' -> 'Уцененные товары'")
    public void navigateToDiscountedProducts() {
        logStep("Шаг 2: Перейти на страницу 'Каталог' -> 'Уцененные товары'");
        citilinkPage.selectDiscountedProductsCategory();
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Каталог уцененных товаров – интернет-магазин бытовой техники и электроники СИТИЛИНК - Москва"));
        logStep("Страница с уцененными товарами загружена \n");
    }

    @Step("Шаг 3. Нажать на 'Смартфоны'")
    public void navigateToDiscountedSmartphones() {
        logStep("Шаг 3: Нажать на 'Смартфоны'");
        citilinkPage.selectDiscountedSmartphones();
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Смартфоны - купить в СИТИЛИНК"));
        logStep("Страница с уцененными смартфонами загружена \n");
    }

    @Step("Шаг 4. Запомнить первый элемент и его цену из списка")
    public void rememberFirstProductDetails() {
        logStep("Шаг 4: Запомнить первый элемент и его цену из списка");
        citilinkPage.rememberFirstProductDetails();
        logStep("Первый товар и его цена запомнены \n");
    }

    @Step("Шаг 5. Добавить товар в Избранное")
    public void addFirstProductToFavorites() {
        logStep("Шаг 5: Добавить товар в Избранное");
        citilinkPage.addFirstProductToFavorites();
        logStep("Сервис требует авторизацию для добавления товара в избранное \n");
    }

}
