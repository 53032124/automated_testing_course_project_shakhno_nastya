package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.interactions.Actions;
import utils.DriverChromeStart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class YandexMarketPage extends DriverChromeStart {

    public String marketURL = "https://market.yandex.ru";
    @FindBy(xpath = "//a[@class = 'focus-ring' and @data-auto='logoYandexLink']")
    public WebElement logoMarket;
    @FindBy(xpath = "//span[contains(text(),'Каталог')]")
    public WebElement catalogButton;

    @FindBy(xpath = "//span[contains(text(),'Электроника') and @class ='_3W4t0']")
    public WebElement electronicsElement;

    @FindBy(xpath = "//a[contains(text(),'Планшеты') and @class='_2re3U ltlqD _2TBT0']")
    public WebElement tablets;

    @FindBy(xpath = "//span[contains(text(),'Samsung')]/ancestor::label")
    public WebElement samsungFilter;

    @FindBy(xpath = "//button[contains(text(),'подешевле') and contains(@class,'_3_4D9 cia-vs cia-cs')]")
    public WebElement sortByPriceAsc;

    @FindBy(xpath = "//h3[@role = 'link']")
    public List<WebElement> productTitles;

    @FindBy(xpath = "//span[@class= '_1ArMm']")
    public List<WebElement> productPrices;

    @FindBy(xpath = "//input[@id='header-search']")
    public WebElement searchInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement searchButton;

    private String rememberedProductName;
    private String rememberedProductPrice;

    public YandexMarketPage(WebDriver driver) {
        PageFactory.initElements(DriverChromeStart.driver, this);
    }


        public void goToTablets() {
            // Ожидание появления и кликабельности кнопки каталога
            logStep("Переход в каталог товаров Яндекс Маркета");
            waitElement("//span[contains(text(),'Каталог')]");
            catalogButton.click();

            // Ожидание появления элемента электроники и перемещение к нему
            logStep("Наведение курсора на раздел Электроника");
            waitElement("//span[contains(text(),'Электроника') and @class ='_3W4t0']");
            Actions actions = new Actions(driver);
            actions.moveToElement(electronicsElement).perform();
            // Ожидание кликабельности элемента планшетов и переход к нему

            logStep("Переход в раздеел Планшеты");
            waitElement("//a[contains(text(),'Планшеты') and @class='_2re3U ltlqD _2TBT0']");
            tablets.click();
        }


    public void filterBySamsung() {
        samsungFilter.click();
    }

    public void sortByPriceAscending() {
        sortByPriceAsc.click();
    }

    public void logFirstFiveProducts() {
        updateProductLists();

        logStep("Всего продуктов: " + productTitles.size());
        logStep("Всего цен: " + productPrices.size() + "\n");

        if (productTitles.size() >= 5 && productPrices.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                String title = productTitles.get(i).getText();
                String price = productPrices.get(i).getText();
                logStep("Планшет " + (i + 1) + ": " + title + " - " + price);
            }
        } else {
            logStep("Найдено менее 5 товаров");
            Assertions.fail("Найдено менее 5 товаров");
        }
    }


    public void rememberSecondProduct() {
        updateProductLists();

        rememberedProductName = productTitles.get(1).getText();
        rememberedProductPrice = productPrices.get(1).getText();
        logStep("Название продукта: " + rememberedProductName + ", цена которого равна: " + rememberedProductPrice );
    }

    public void searchForRememberedProduct() {
        searchInput.sendKeys(rememberedProductName);
        searchButton.click();

        waitElement("//h3[@role='link']");
        updateProductLists();

        WebElement searchResultTitle = productTitles.get(0);
        WebElement searchResultPrice = productPrices.get(0);

        Assertions.assertEquals(rememberedProductName, searchResultTitle.getText());
        Assertions.assertEquals(rememberedProductPrice, searchResultPrice.getText());
        logStep("Найден продукт: " + rememberedProductName + ", цена которого равна: " + rememberedProductPrice );

    }
    // Метод для проверки отображения логотипа
    public boolean isLogoDisplayed() {
        return logoMarket.isDisplayed();
    }
    private void updateProductLists() {
        // Update product lists to reflect the current state of the page
        productTitles = driver.findElements(By.xpath("//h3[@role='link']"));
        productPrices = driver.findElements(By.xpath("//span[@class='_1ArMm']"));
    }
}
