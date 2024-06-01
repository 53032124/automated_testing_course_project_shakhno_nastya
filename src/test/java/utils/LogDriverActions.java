package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverStart;

public class LogDriverActions extends DriverStart implements WebDriverListener {

    private static Logger _loger = (Logger) LoggerFactory.getLogger(WebDriver.class);

    @Override
    public void beforeClick(WebElement element) {
        Allure.step("Клик на " + element.getText());
    }

    // add method to sout info when we put informations into field
}
