package tests.task4;

import pages.task4.CitilinkPageForTestCase2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ListenerForTests;
import utils.DriverChromeStart;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

@Feature("Задание 4")
@ExtendWith(ListenerForTests.class)
public class CitilinkTestCase2 extends DriverChromeStart {

    CitilinkPageForTestCase2 citilinkPage;

    @Owner("Анастасия Шахно")
    @DisplayName("Тест-кейс 2 (добавление товара в корзину с выставлением фильтра 'Забрать через 5 минут')")
    @Test
    public void testAddProductToCartWithFilter() {
        citilinkPage = new CitilinkPageForTestCase2(driver);
        driver.get(citilinkPage.citilinkURL);

        CitilinkTestCase1.verifyHomePage();
        navigateToProcessors();
        applyFilterPickupIn5Minutes();
        addFirstProductToCart();
        //goToCartAndVerifyProduct();
    }



    @Step("Шаг 2. Перейти на страницу 'Процессоры'")
    public void navigateToProcessors() {
        logStep("Шаг 2: Перейти на страницу 'Процессоры'");
        citilinkPage.selectProcessorsCategory();

        String pageTitle = citilinkPage.pageTitle.getText();
        Assertions.assertEquals("Процессоры", pageTitle);

        logStep("Страница с процессорами загружена \n");
    }

    @Step("Шаг 3. Выставить фильтр 'Забрать через 5 минут'")
    public void applyFilterPickupIn5Minutes() {
        logStep("Шаг 3: Выставить фильтр 'Забрать через 5 минут'");
        citilinkPage.applyPickupIn5MinutesFilter();

        logStep("Фильтр 'Забрать через 5 минут' применен \n");
    }

    @Step("Шаг 4. Первый в списке товар добавить в корзину")
    public void addFirstProductToCart() {
        logStep("Шаг 4: Первый в списке товар добавить в корзину");
        citilinkPage.addFirstProductToCart();

        logStep("Первый товар добавлен в корзину \n");
    }

    @Step("Шаг 5. Перейти в корзину и проверить наличие товара")
    public void goToCartAndVerifyProduct() {
        logStep("Шаг 5: Перейти в корзину и проверить наличие товара");
        citilinkPage.goToCartAndVerifyProduct();

        logStep("Проверка товара в корзине успешно завершена \n");
    }
}
