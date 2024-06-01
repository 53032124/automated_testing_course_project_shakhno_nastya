package pages;

import utils.DriverChromeStart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SchedulePage extends DriverChromeStart {

    @FindBy(xpath = "//a[text()='Расписания']")
    public WebElement scheduleButton;

    @FindBy(xpath = "//a[text()='Смотрите на сайте']")
    public WebElement viewOnSiteButton;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchField;

    @FindBy(xpath = "//div[@class='search-result-item']")
    public WebElement searchResult;

    public SchedulePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void goToSchedulePage() {
        scheduleButton.click();
    }

    public void viewOnSite() {
        viewOnSiteButton.click();
    }

    public void searchGroup(String groupNumber) {
        searchField.sendKeys(groupNumber);
    }

    public void selectGroup() {
        searchResult.click();
    }
}
