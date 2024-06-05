package tests;

import pages.YandexMarketPage;

import utils.DriverChromeStart;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ListenerForTests;

@Feature("Тест 3")
@ExtendWith(ListenerForTests.class)
public class YandexMarketTests extends DriverChromeStart {

    YandexMarketPage marketPage;

    @Owner("Nastya Shakhno")
    @DisplayName("Проверка поиска товара на Яндекс.Маркете")
    @Test
    public void searchProductTest() {
        marketPage = new YandexMarketPage(driver);
        driver.get(marketPage.marketURL);

        verifyHomePage();
        navigateToTablets();
        filterBySamsung();
        sortByPriceAscending();
        logFirstFiveProducts();
        rememberSecondProduct();
        searchForSecondProduct();
    }

    @Step("Шаг 1. Проверить главную страницу Яндекс.Маркета")
    public void verifyHomePage() {
        logStep("Шаг 1: Проверить главную страницу Яндекс.Маркета");

        // Проверка логотипа
        Assertions.assertTrue(marketPage.isLogoDisplayed(), "Логотип Яндекс.Маркета не отображается");

        logStep("Главная страница успешно проверена \n");
    }

    @Step("Шаг 2. Перейти в категорию 'Планшеты'")
    public void navigateToTablets() {
        logStep("Шаг 2: Перейти в категорию 'Планшеты'");
        marketPage.goToTablets();
        String pageTitle = driver.getTitle();
        Assertions.assertTrue(pageTitle.contains("Планшеты"));
        logStep("Страница Планшеты успешно открыта \n");
    }

    @Step("Шаг 3. Применить фильтр по производителю 'Samsung'")
    public void filterBySamsung() {
        logStep("Шаг 3: Применить фильтр по производителю 'Samsung'");
        marketPage.filterBySamsung();
        logStep("Фильтр 'Samsung' успешно применен \n");
    }

    @Step("Шаг 4. Установить сортировку по возрастанию цены")
    public void sortByPriceAscending() {
        logStep("Шаг 4: Установить сортировку по возрастанию цены");
        marketPage.sortByPriceAscending();
        logStep("Сортировка по цене успешно установлена \n");
    }

    @Step("Шаг 5. Вывести в лог первые 5 товаров")
    public void logFirstFiveProducts() {
        logStep("Шаг 5: Вывести в лог первые 5 товаров");
        marketPage.logFirstFiveProducts();
        logStep("Первые 5 товаров успешно выведены в лог \n");
    }

    @Step("Шаг 6. Запомнить второй товар из списка")
    public void rememberSecondProduct() {
        logStep("Шаг 6: Запомнить второй товар из списка");
        marketPage.rememberSecondProduct();
        logStep("Второй товар успешно запомнен \n");
    }

    @Step("Шаг 7. Искать запомненный товар")
    public void searchForSecondProduct() {
        logStep("Шаг 7: Искать запомненный товар");
        marketPage.searchForRememberedProduct();
        logStep("Запомненный товар успешно найден первым \n");
    }
}
