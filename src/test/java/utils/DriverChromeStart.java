package utils;

import com.google.common.base.Predicate;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

abstract public class DriverChromeStart {

    public static WebDriver driver;
    private static ChromeOptions chromeOptions;
    private static final Logger logger = LoggerFactory.getLogger(DriverChromeStart.class);

    private static WebDriverWait wait;

    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void initBefore() {
        setUp();
    }

    public void logStep(String message) {
        logger.info(message);
        Allure.step(message);
    }

    public void waitElement(String path) {
        wait.until(visibilityOfElementLocated(By.xpath(path)));
    }
    public static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
}
