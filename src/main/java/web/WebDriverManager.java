package web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public final class WebDriverManager {
    private WebDriver driver;
    private static WebDriverManager defaultManager = new WebDriverManager();

    private WebDriverManager(){
        driver = new ChromeDriver();
    }

    public static WebDriver get(){
        return defaultManager.getDriver();
    }

    public static WebDriverManager getDefaultManager() {
        return defaultManager;
    }

    public WebDriver getDriver(){
        return driver;
    }
}
