package pages;

import io.qameta.allure.internal.shadowed.jackson.core.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import utils.DriverChromeStart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SchedulePage extends DriverChromeStart {

    public String mospolytechURL = "https://mospolytech.ru/";

    @FindBy(xpath = "//ul[@class='user-nav__list']//li[3]//a[contains(@title, 'Расписание')]") // XPath для кнопки расписания
    public WebElement schedulesButton;

    @FindBy(xpath = "//div[@class='entry-step__actions animate']//a[contains(@href, 'rasp.dmami.ru')]")
    public WebElement viewOnWebsiteButton;
    @FindBy(className = "groups")
    public WebElement searchField;

    @FindBy(xpath = "//div[@class='found-groups row not-print']//div[@id='221-361']")
    public WebElement firstSearchResult;


    @FindBy(xpath = "//h2")
    public WebElement pageTitle;

    public SchedulePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickSchedulesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(schedulesButton));
        schedulesButton.click();
    }

    public void clickViewOnWebsiteButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(viewOnWebsiteButton));
        viewOnWebsiteButton.click();
    }


    public void searchForGroup(String groupNumber) {
        searchField.sendKeys(groupNumber);
    }

    public void clickFirstSearchResult() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("221-361")));
        WebElement firstResult = driver.findElement(By.id("221-361"));
        firstResult.click();
    }


    public String getCurrentDayOfWeek() {
        return java.time.LocalDate.now().getDayOfWeek().name().toLowerCase();
    }

    public boolean isCurrentDayHighlighted() {
        String currentDay = getCurrentDayOfWeek();
        WebElement currentDayElement = driver.findElement(By.className(currentDay));
        return currentDayElement.getAttribute("class").contains("highlighted");
    }
    public void scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
    }
}
