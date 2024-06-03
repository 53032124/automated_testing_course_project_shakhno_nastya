package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.SchedulePage;
import utils.DriverChromeStart;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ListenerForTests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Feature("Тестирование расписания")
@ExtendWith(ListenerForTests.class)
public class ScheduleTests extends DriverChromeStart {

    SchedulePage schedulePage;

    @Owner("Анастасия Шахно")
    @DisplayName("Тестирование страницы расписания на сайте Мосполитеха")
    @Test
    public void scheduleTest() {
        schedulePage = new SchedulePage(driver);

        driver.get(schedulePage.mospolytechURL);

        goToMoscowPolytex();
        navigateToSchedulePage();
        openScheduleSearch();
        searchForGroup();
        openGroupScheduleverifyCurrentDayHighlighted();
    }

    @Step("Шаг 1. Проверить заголовок страницы, что подтверждает загрузку страницы")
    public void goToMoscowPolytex() {
        logStep("Шаг 1: Проверить заголовок страницы");
        String pageTitle = schedulePage.pageTitle.getText();
        Assertions.assertEquals("Флагман проектного обучения в России", pageTitle);
        logStep("Заголовок страницы успешно проверен \n");
    }
    @Step("Шаг 2. Перейти на страницу расписания")
    public void navigateToSchedulePage() {
        logStep("Шаг 2: Перейти на страницу расписания");
        schedulePage.clickSchedulesButton();
        logStep("Успешный переход на страницу расписания \n");
    }


    @Step("Шаг 3. Открыть страницу поиска расписания и пролистать вниз")
    public void openScheduleSearch() {
        logStep("Шаг 3: Открыть страницу поиска расписания");
        schedulePage.scrollPageDown(); // Пролистываем страницу вниз

        // Получаем текущее окно браузера
        String currentWindow = driver.getWindowHandle();

        // Нажимаем на кнопку "Смотрите на сайте"
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).click(schedulePage.viewOnWebsiteButton).keyUp(Keys.CONTROL).perform();

        // Ждем, пока новая вкладка загрузится
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Переключаемся на новую вкладку
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        tabs.remove(currentWindow);
        driver.switchTo().window(tabs.get(0));

        logStep("Успешное открытие страницы поиска расписания и пролистывание вниз \n");
    }


    @Step("Шаг 4. Ввести номер группы")
    public void searchForGroup() {
        logStep("Шаг 4: Ввести номер группы");
        String groupNumber = "221-361"; // Ваш номер группы
        schedulePage.searchForGroup(groupNumber);
        logStep("Номер группы успешно введен: " + groupNumber);

        // Подтверждаем ввод
        schedulePage.searchField.sendKeys(Keys.ENTER);
        logStep("Ввод номера группы подтвержден \n");

        // Проверяем, что отобразилась только одна группа в контейнере
        List<WebElement> groupElements = driver.findElements(By.xpath("//div[@class='found-groups row not-print']"));
        Assertions.assertEquals(1, groupElements.size(), "Найдено неверное количество групп");
    }


    @Step("Шаг 5. Открыть расписание группы")
    public void openGroupScheduleverifyCurrentDayHighlighted() {
        logStep("Шаг 5: Открыть расписание группы");
        schedulePage.scrollPageDown();
        schedulePage.clickFirstSearchResult();
        logStep("Шаг 5.1: Проверить, что текущий день выделен");
        schedulePage.scrollPageDown();
        Assertions.assertTrue(schedulePage.isCurrentDayHighlighted(), "Текущий день не выделен");
        logStep("Текущий день успешно выделен");
        logStep("Расписание группы открыто успешно \n");
    }


}
