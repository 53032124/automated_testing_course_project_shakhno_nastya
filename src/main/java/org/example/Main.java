package org.example;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Main {
    @FindBy(xpath = "//span[contains(text(),'Каталог')]")
    public static WebElement catalogButton;
    public static void main(String[] args) {
        System.out.println(catalogButton.getText());
    }
}