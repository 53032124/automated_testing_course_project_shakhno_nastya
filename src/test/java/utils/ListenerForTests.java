package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.DriverChromeStart.driver;

public class ListenerForTests implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (driver != null) { // Проверка на null
            Allure.getLifecycle().addAttachment(
                    "Скрин во время падения теста", "image/png", "png",
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
            );

            Allure.addAttachment("Логи в результате падения теста: ", String.valueOf(driver.manage().logs().get(LogType.BROWSER).getAll()));
            //DriverChromeStart.driver.quit();
        }
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MMM-dd-HH-mm-ss");
            String pathName = "testFailed-(" + context.getDisplayName().replaceAll(" ", "-").replaceAll("[\\/\\?]", "_") + ")-" + LocalDateTime.now().format(format) + ".png";
            FileUtils.copyFile(srcFile, new File(pathName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WebDriverManager.chromedriver().quit();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (driver != null) { // Проверка на null
            Allure.getLifecycle().addAttachment(
                    "Скрин в результате успешного прохождения теста", "image/png", "png",
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
            );
            Allure.addAttachment("Логи в результате успешного прохождения теста: ", String.valueOf(driver.manage().logs().get(LogType.BROWSER).getAll()));
            DriverChromeStart.driver.quit();
        }
        WebDriverManager.chromedriver().quit();
    }

}
