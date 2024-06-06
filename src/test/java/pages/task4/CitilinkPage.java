package pages.task4;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverChromeStart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class CitilinkPage extends DriverChromeStart {

    public String citilinkURL = "https://www.citilink.ru/";

    @FindBy(xpath = "//h1")
    public WebElement pageTitle;

    @FindBy(xpath = "//h1[@class='elbnj820 eml1k9j0 app-catalog-kfo60a e1gjr6xo0']")
    public WebElement pageTitleApple;
    @FindBy(xpath = "//span[text()='Каталог товаров']")
    public WebElement catalogMenu;

    @FindBy(xpath = "(//span[text()='Смартфоны и планшеты' and @class = 'e19upju70 e106ikdt0 css-1t3lgls e1gjr6xo0'])[2]")
    private WebElement smartphonesAndTabletsCategory;

    @FindBy(xpath = "//span[text()='APPLE iPhone']")
    public WebElement appleIphoneCategory;

    @FindBy(xpath = "(//span[@class = 'e1c14o6m0 e106ikdt0 app-catalog-mmyg1s e1gjr6xo0'])[1]")
    public WebElement sortByPriceAsc;

    @FindBy(xpath = "//a[contains(@class, 'app-catalog-9gnskf e1259i3g0')]")
    public List<WebElement> productTitles;

    @FindBy(xpath = "//span[contains(@class, 'e1j9birj0 e106ikdt0 app-catalog-56qww8 e1gjr6xo0')]")
    public List<WebElement> productPrices;

    public CitilinkPage(WebDriver driver) {
        PageFactory.initElements(DriverChromeStart.driver, this);
    }

    public void selectCategory() {
        logStep("Переход в католог");
        catalogMenu.click();
        waitForPageLoad(driver); // Ожидание загрузки после клика на каталог

        logStep("Навести курссор на категорию Смартфоны и планшеты ");
        waitElement("(//span[text()='Смартфоны и планшеты' and @class = 'e19upju70 e106ikdt0 css-1t3lgls e1gjr6xo0'])[2]");
        Actions actions = new Actions(driver);
        actions.moveToElement(smartphonesAndTabletsCategory).perform();

        logStep("Нажатие на APPLE iPhone");
        appleIphoneCategory.click();
        waitElement("//h1[@class='elbnj820 eml1k9j0 app-catalog-kfo60a e1gjr6xo0']");
        String pageTitleText = pageTitleApple.getText();
        Assertions.assertEquals("Apple iPhone", pageTitleText, "Заголовок страницы не соответствует ожидаемому");

    }

    public void sortByPriceAsc() {
        sortByPriceAsc.click();
        waitForPageLoad(driver); // Ожидание загрузки после сортировки
        updateProductLists();
        verifySortedProducts();
    }

    public void logFirstFiveProducts() {
        updateProductLists();

        logStep("Всего продуктов: " + productTitles.size());
        logStep("Всего цен: " + productPrices.size() + "\n");

        if (productTitles.size() >= 5 && productPrices.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                String title = productTitles.get(i).getText();
                String price = productPrices.get(i).getText();
                logStep("Товар " + (i + 1) + ": " + title + " - " + price);
            }
        } else {
            logStep("Найдено менее 5 товаров");
            Assertions.fail("Найдено менее 5 товаров");
        }
    }

    protected void updateProductLists() {
        waitForPageLoad(driver);

        // Добавление небольшой задержки
        try {
            Thread.sleep(2000); // Задержка 2 секунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Явное ожидание для списка элементов
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ожидание появления первого продукта в списке
        wait.until(driver -> driver.findElements(By.xpath("//a[contains(@class, 'app-catalog-9gnskf e1259i3g0')]")).size() > 0);
        productTitles = driver.findElements(By.xpath("//a[contains(@class, 'app-catalog-9gnskf e1259i3g0')]"));

        // Ожидание появления первой цены в списке
        wait.until(driver -> driver.findElements(By.xpath("//span[contains(@class, 'e1j9birj0 e106ikdt0 app-catalog-56qww8 e1gjr6xo0')]")).size() > 0);
        productPrices = driver.findElements(By.xpath("//span[contains(@class, 'e1j9birj0 e106ikdt0 app-catalog-56qww8 e1gjr6xo0')]"));

        // Логгирование количества продуктов и цен для отладки
        logStep("Количество продуктов после обновления: " + productTitles.size());
        logStep("Количество цен после обновления: " + productPrices.size());
    }


    private void verifySortedProducts() {
        logStep("Проверка сортировки товаров по возрастанию цены");
        for (int i = 0; i < 4; i++) {
            String priceText1 = productPrices.get(i).getText().replaceAll("[^0-9]", "");
            String priceText2 = productPrices.get(i + 1).getText().replaceAll("[^0-9]", "");

            int price1 = Integer.parseInt(priceText1);
            int price2 = Integer.parseInt(priceText2);
            Assertions.assertTrue(price2 >= price1, "Ошибка: Цена товара " + (i + 2) + " меньше цены товара " + (i + 1));
        }
        logStep("Сортировка товаров успешно проверена");
    }
    public void scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
    }

    public static void scrollPageUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,0)");
    }

}
