package tests;

import pages.TodoPage;

import utils.DriverStart;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TestListener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Feature("Тесты 1")
@ExtendWith(TestListener.class)
public class TodoTests extends DriverStart {

    TodoPage todoPage;

    @Owner("Nastya Shakhno")
    @DisplayName(value="Тестирование списка дел \"LambdaTest Sample App\"")
    @Test
    public void toDoTest() {
        todoPage = new TodoPage(driver);
        driver.get(todoPage.toDoURL);

        verifyPageTitle();
        verifyTextRemaining();
        verifyFirstItemNotStrikethrough();
        toggleFirstItemCheckbox();
        repeatStepsForAllItems();
        addNewItemToList();
        toggleNewItemCheckbox();
    }

    @Step("Шаг 1. Проверить заголовок страницы")
    public void verifyPageTitle() {
        logStep("Шаг 1: Проверить заголовок страницы");
        String pageTitle = todoPage.pageTitle.getText();
        Assertions.assertEquals("LambdaTest Sample App", pageTitle);
        logStep("Заголовок страницы успешно проверен \n");
    }

    @Step("Шаг 2. Проверить, что отображается текст: \"5 of 5 remaining\"")
    public void verifyTextRemaining() {
        logStep("Шаг 2: Проверить текст '5 of 5 remaining'");
        String text = todoPage.textRemaining.getText();
        Assertions.assertEquals("5 of 5 remaining", text);
        logStep("Текст '5 of 5 remaining' успешно проверен \n");
    }

    @Step("Шаг 3. Убедиться, что первый элемент списка не зачеркнут")
    public void verifyFirstItemNotStrikethrough() {
        logStep("Шаг 3: Проверить, что первый элемент не отмечен");
        String classFirstItem = todoPage.findItemClass(0);
        Assertions.assertEquals("done-false", classFirstItem);
        logStep("Первый элемент успешно проверен \n");
    }

    @Step("Шаг 4. Установить галочку у первого элемента")
    public void toggleFirstItemCheckbox() {
        logStep("Шаг 4: Отметить первый элемент");

        todoPage.checkBoxClick(0);

        String classFirstItem = todoPage.findItemClass(0);
        Assertions.assertEquals("done-true", classFirstItem);

        String countOfElements = todoPage.textRemaining.getText();
        Assertions.assertEquals("4 of 5 remaining", countOfElements);

        logStep("Первый элемент успешно выбран \n");
    }

    @Step("Шаг 5. Повторить шаги 3 и 4 для остальных элементов списка")
    public void repeatStepsForAllItems() {
        logStep("Шаг 5: Отметить все элементы");

        int listSize = todoPage.todoList.size();

        for(int i = 1; i < listSize; i++) {
            String classCurrentItem = todoPage.findItemClass(i);
            Assertions.assertEquals("done-false", classCurrentItem);

            todoPage.checkBoxClick(i);

            classCurrentItem = todoPage.findItemClass(i);
            Assertions.assertEquals("done-true", classCurrentItem);

            String countOfElements = todoPage.textRemaining.getText();
            int left = 5 - i - 1;
            Assertions.assertEquals(left + " of 5 remaining", countOfElements);
        }

        logStep("Все элементы успешно отмечены \n");
    }

    @Step("Шаг 6. Добавить новый элемент в список")
    public void addNewItemToList() {
        logStep("Шаг 6: Добавить новый элемент");
        todoPage.inputField.sendKeys("New Todo Item");
        todoPage.submitButton.click();

        String classFirstItem = todoPage.findItemClass(5);
        Assertions.assertEquals("done-false", classFirstItem);

        String countOfElements = todoPage.textRemaining.getText();
        Assertions.assertEquals("1 of 6 remaining", countOfElements);
        logStep("Новый элемент успешно добавлен \n");
    }

    @Step("Шаг 7. Установить галочку у нового элемента")
    public void toggleNewItemCheckbox() {
        logStep("Шаг 7: Отметить новый элемент");
        todoPage.checkBoxClick(5);

        String classFirstItem = todoPage.findItemClass(5);
        Assertions.assertEquals("done-true", classFirstItem);

        String countOfElements = todoPage.textRemaining.getText();
        Assertions.assertEquals("0 of 6 remaining", countOfElements);
        logStep("Новый элемент успешно отмечен \n");
    }

    //allure serve target/allure-results
}