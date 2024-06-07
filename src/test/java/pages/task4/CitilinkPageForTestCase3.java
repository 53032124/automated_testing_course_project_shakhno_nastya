package pages.task4;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CitilinkPageForTestCase3 extends CitilinkPage {

    @FindBy(xpath = "//span[text()='Каталог товаров']")
    private WebElement catalogMenu;

    @FindBy(xpath = "(//span[text()='Уцененные товары'])[2]")
    private WebElement discountedProductsCategory;

    @FindBy(xpath = "//a[text()='Смартфоны']")
    private WebElement discountedSmartphonesCategory;

    @FindBy(xpath = "(//a[contains(@data-meta-name,'Snippet__title')])[1]")
    private WebElement firstDiscountedProduct;

    @FindBy(xpath = "(//span[@class='e1j9birj0 e106ikdt0 app-catalog-56qww8 e1gjr6xo0'])[1]")
    private WebElement firstDiscountedProductPrice;

    @FindBy(xpath = "(//button[contains(@class,'etd7ecp0 app-catalog-11xrwzj e8hswel0')])[1]")
    private WebElement addToFavoritesButton;

    @FindBy(xpath = "//div[@class = 'css-1tr3ug9.eyoh4ac0']")
    private WebElement authorizationRequiredBanner;

    private String rememberedProductName;
    private String rememberedProductPrice;

    public CitilinkPageForTestCase3(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectDiscountedProductsCategory() {
        waitForPageLoad(driver);
        logStep("Переход в категорию 'Уцененные товары'");
        catalogMenu.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(discountedProductsCategory));
        discountedProductsCategory.click();
        waitForPageLoad(driver);
    }

    public void selectDiscountedSmartphones() {
        waitForPageLoad(driver);
        logStep("Переход в категорию 'Смартфоны'");
        discountedSmartphonesCategory.click();
        waitForPageLoad(driver);
    }

    public void rememberFirstProductDetails() {
        logStep("Запоминание первого товара и его цены");
        rememberedProductName = firstDiscountedProduct.getText();
        rememberedProductPrice = firstDiscountedProductPrice.getText();
    }

    public void addFirstProductToFavorites() {
        logStep("Добавление первого товара в избранное");
        addToFavoritesButton.click();
        logStep("Проверка появления плашки 'Требуется авторизация'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assertions.assertNotNull(authorizationRequiredBanner, "Плашка 'Требуется авторизация' не появилась");
        logStep("Название товара: " + rememberedProductName + ", Цена товара: " + rememberedProductPrice);
    }


}
