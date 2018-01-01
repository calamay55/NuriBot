import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloWorlds {
    public static void main(String[] args){
        HelloSelenium();
    }

    private static void HelloSelenium() {
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.puzzle-nurikabe.com/");
    }
}
