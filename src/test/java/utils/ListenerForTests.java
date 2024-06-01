package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;

public class ListenerForTests implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.getLifecycle().addAttachment(
                "Скрин во время падения теста", "image/png", "png",
                ((TakesScreenshot) DriverChromeStart.driver).getScreenshotAs(OutputType.BYTES)
        );

        Allure.addAttachment("Логи в  результате падения теста: ", String.valueOf(DriverChromeStart.driver.manage().logs().get(LogType.BROWSER).getAll()));
        WebDriverManager.chromedriver().quit();
//        Abstract._driver.quit();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        Allure.getLifecycle().addAttachment(
                "Скрин в результате успешного прохождения теста", "image/png", "png",
                ((TakesScreenshot) DriverChromeStart.driver).getScreenshotAs(OutputType.BYTES)
        );
        Allure.addAttachment("Логи в результате успешного прохождения теста: ", String.valueOf(DriverChromeStart.driver.manage().logs().get(LogType.BROWSER).getAll()));
        WebDriverManager.chromedriver().quit();
//        Abstract._driver.quit();
    }
}
