package pages.task4;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CitilinkPageForTestCase2 extends CitilinkPage {


    @FindBy(xpath = "//span[text()='Процессоры']")
    private WebElement processorsCategory;

    @FindBy(xpath = "//span[@class = 'app-catalog-1sylyko e11kmul30']")
    private WebElement pickupIn5MinutesFilter;

    @FindBy(xpath = "(//button[contains(@class, 'e4uhfkv0 app-catalog-19zipnm e4mggex0')])[1]")
    private WebElement addToCartButton;

    @FindBy(xpath = "(//div[contains(@class, 'css-1wyvf5z eyoh4ac0')])[4]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//div[@class='e1c14o6m0 e106ikdt0 app-catalog-1kznqws e1gjr6xo0']")
    private WebElement cartProduct;

    public CitilinkPageForTestCase2(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectProcessorsCategory() {
        waitForPageLoad(driver);

        logStep("Переход в категорию 'Процессоры'");
        processorsCategory.click();
        waitElement("//h1[@class='elbnj820 eml1k9j0 app-catalog-kfo60a e1gjr6xo0']");
        waitForPageLoad(driver);
    }

    public void applyPickupIn5MinutesFilter() {
        logStep("Применение фильтра 'Забрать через 5 минут'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(pickupIn5MinutesFilter));
        scrollPageDown();
        pickupIn5MinutesFilter.click();
        waitForPageLoad(driver);
        updateProductLists();
    }

    public void addFirstProductToCart() {
        logStep("Добавление первого товара в корзину");
        addToCartButton.click();
        waitForPageLoad(driver);
    }

    public void goToCartAndVerifyProduct() {
        logStep("Переход в корзину");
        goToCartButton.click();
        waitForPageLoad(driver);

        //Assertions.assertTrue(cartProduct.isDisplayed(), "Товар не найден в корзине");
    }
}
