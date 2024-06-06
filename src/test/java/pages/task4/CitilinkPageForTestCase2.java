package pages.task4;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CitilinkPageForTestCase2 extends CitilinkPage {

    @FindBy(xpath = "//h1[@class='elbnj820 eml1k9j0 app-catalog-kfo60a e1gjr6xo0']")
    private WebElement processorsh1;

    @FindBy(xpath = "(//span[contains(text(),'товаров')])[2]")
    private WebElement countproduct;

    @FindBy(xpath = "//button[@class='e1nu7pom0 css-ony2li e4mggex0']")
    private WebElement exit;


    private int countproductBeforeFilter;
    private int countproductAfterFilter;

    @FindBy(xpath = "//span[text()='Процессоры']")
    private WebElement processorsCategory;

    @FindBy(xpath = "(//span[@class = 'app-catalog-1sylyko e11kmul30'])[2]")
    private WebElement pickupIn5MinutesFilter;

    @FindBy(xpath = "(//a[contains(@data-meta-name,'Snippet__title')])[1]")
    private WebElement addToCartText;

    private String addToCartTextString;

    @FindBy(xpath = "(//button[contains(@class, 'e4uhfkv0 app-catalog-19zipnm e4mggex0')])[1]")
    private WebElement addToCartButton;

    @FindBy(xpath = "(//div[contains(@class, 'css-1wyvf5z eyoh4ac0')])[4]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//span[@class='e1ys5m360 e106ikdt0 css-56qww8 e1gjr6xo0']")
    private WebElement cartProduct;

    public CitilinkPageForTestCase2(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public int getCountProduct() {
        String countText = countproduct.getText().replaceAll("\\D+", ""); // Removing all non-numeric characters
        return Integer.parseInt(countText);
    }

    public String getTextCard() {
        return addToCartText.getAttribute("title");
    }

    public void selectProcessorsCategory() {
        waitForPageLoad(driver);

        logStep("Переход в категорию 'Процессоры'");
        processorsCategory.click();


        waitForPageLoad(driver);


        countproductBeforeFilter = getCountProduct();
    }

    public void applyPickupIn5MinutesFilter() {
        logStep("Применение фильтра 'Забрать через 5 минут'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(pickupIn5MinutesFilter));
        scrollPageDown();

        pickupIn5MinutesFilter.click();

        boolean filterApplied = false;
        for (int i = 0; i < 5; i++) {
            countproductAfterFilter = getCountProduct();

            if (countproductBeforeFilter == countproductAfterFilter) {
                filterApplied = true;
                logStep("Фильтр применился");
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!filterApplied) {
            logStep("Фильтр не применился");
            Assertions.fail("Фильтр не применился");
        }
    }

    public void addFirstProductToCart() {
        logStep("Добавление первого товара в корзину");
        addToCartTextString = getTextCard();
        addToCartButton.click();

        waitForPageLoad(driver);
    }

    public void goToCartAndVerifyProduct() {
        waitElement("//button[@class='e1nu7pom0 css-ony2li e4mggex0']");
        exit.click();
        logStep("Переход в корзину");
        goToCartButton.click();

        String string1 = cartProduct.getText().replaceAll(" ", "");
        String string = addToCartTextString.replaceAll(" ","");
        if (string.equals(string1)) {
            logStep("Товар совпадает");
        } else {
            logStep("Товар не совпадает");
            Assertions.fail("Товар не совпадает");
        }
    }
}
