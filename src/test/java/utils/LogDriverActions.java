package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDriverActions extends DriverChromeStart implements WebDriverListener {

    private static Logger _loger = (Logger) LoggerFactory.getLogger(WebDriver.class);

    @Override
    public void beforeClick(WebElement element) {
        Allure.step("Клик на " + element.getText());
    }

}
