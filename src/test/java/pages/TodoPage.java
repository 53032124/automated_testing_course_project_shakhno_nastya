package pages;

import utils.LogDriverActions;
import utils.DriverChromeStart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.List;

public class TodoPage extends DriverChromeStart {

    public String toDoURL = "https://lambdatest.github.io/sample-todo-app/";

    @FindBy(xpath = "//h2")
    public WebElement pageTitle;

    @FindBy(xpath = "//span[1]")
    public WebElement textRemaining;

    @FindBy(xpath = "//ul/child::*")
    public List<WebElement> todoList;

    @FindBy(xpath = "//form/input[@type='text']")
    public WebElement inputField;

    @FindBy(xpath = "//form/input[@type='submit']")
    public WebElement submitButton;

    public TodoPage(WebDriver driver) {
 //       DriverChromeStart.driver = new EventFiringDecorator(new LogDriverActions()).decorate(driver);
        PageFactory.initElements(DriverChromeStart.driver, this);
    }

    public void checkBoxClick(int index) {
        WebElement checkbox = todoList.get(index).findElement(By.tagName("input"));
        checkbox.click();
    }

    public String findItemClass(int index) {
        WebElement item = todoList.get(index).findElement(By.tagName("span"));
        return item.getAttribute("class");
    }
}
